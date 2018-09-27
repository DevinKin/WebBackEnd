package cn.devinkin.mapper;

import cn.devinkin.pojo.TbItemDesc;
import cn.devinkin.pojo.TbItemDescExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbItemDescMapper {
    long countByExample(TbItemDescExample example);

    int deleteByExample(TbItemDescExample example);

    int insert(TbItemDesc record);

    int insertSelective(TbItemDesc record);

    List<TbItemDesc> selectByExampleWithBLOBs(TbItemDescExample example);

    List<TbItemDesc> selectByExample(TbItemDescExample example);

    int updateByExampleSelective(@Param("record") TbItemDesc record, @Param("example") TbItemDescExample example);

    int updateByExampleWithBLOBs(@Param("record") TbItemDesc record, @Param("example") TbItemDescExample example);

    int updateByExample(@Param("record") TbItemDesc record, @Param("example") TbItemDescExample example);

//    TbItemDesc selectByPrimaryKey(Long id);
}