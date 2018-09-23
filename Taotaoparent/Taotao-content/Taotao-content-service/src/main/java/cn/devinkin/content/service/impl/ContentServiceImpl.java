package cn.devinkin.content.service.impl;

import cn.devinkin.common.json.JsonUtils;
import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.content.service.ContentService;
import cn.devinkin.jedis.JedisClient;
import cn.devinkin.mapper.TbContentMapper;
import cn.devinkin.pojo.TbContent;
import cn.devinkin.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: ContentServiceImpl</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 20:22 2018/9/21
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;


    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;

    @Override
    public TaotaoResult addContent(TbContent content) {
        // 补全pojo的属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        // 插入到内容表中
        tbContentMapper.insert(content);
        // 同步redis缓存,删除对应的缓存信息
        jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        // 设置分页信息
        PageHelper.startPage(page, rows);
        TbContentExample example = new TbContentExample();
        // 通过分类id查找内容列表
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = tbContentMapper.selectByExample(example);
        // 取得查询结果
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        // 返回结果
        return result;
    }


    @Override
    public TbContent getContentById(Long id) {
        TbContent tbContent = tbContentMapper.selectByPrimaryKey(id);
        return tbContent;
    }

    @Override
    public void updateContent(TbContent content) {
        // 从数据库中获取原内容
        TbContent tbContent = tbContentMapper.selectByPrimaryKey(content.getId());
        // 修改内容更新时间
        tbContent.setUpdated(new Date());
        // 修改内容属性
        tbContent.setTitle(content.getTitle());
        tbContent.setSubTitle(content.getSubTitle());
        tbContent.setTitleDesc(content.getTitleDesc());
        tbContent.setUrl(content.getUrl());
        tbContent.setPic(content.getPic());
        tbContent.setPic2(content.getPic2());
        tbContent.setContent(content.getContent());
        // 根据内容id进行修改
        tbContentMapper.updateByPrimaryKeyWithBLOBs(tbContent);
        // 同步redis缓存,删除对应的缓存信息
        jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
    }

    @Override
    public void deleteContent(Long[] ids) {
        // 根据id查找对应的内容
        TbContent content = tbContentMapper.selectByPrimaryKey(ids[0]);
        // 得到他的分类id
        Long cid = content.getCategoryId();
        // 遍历id
        for (Long id : ids) {
            tbContentMapper.deleteByPrimaryKey(id);
        }
        // 同步redis缓存,删除对应的缓存信息
        jedisClient.hdel(INDEX_CONTENT, cid.toString());
    }

    @Override
    public List<TbContent> getContentListByCid(Long cid) {
        // 先查询缓存
        // 添加缓存不能影响正常业务略及
        try {
            // 查询缓存
            String json = jedisClient.hget(INDEX_CONTENT, cid + "");
            if (StringUtils.isNotBlank(json)) {
                List<TbContent> contentList = JsonUtils.jsonToList(json, TbContent.class);
                return contentList;
            }
            // 查询到结果
            // 把Json转换为List返回

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 缓存中没有命中,需要查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        // 设置查询条件
        criteria.andCategoryIdEqualTo(cid);

        // 执行查询
        List<TbContent> list = tbContentMapper.selectByExample(example);
        // 把结果添加到缓存
        try {
            jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {

        }
        // 返回结果
        return list;
    }
}
