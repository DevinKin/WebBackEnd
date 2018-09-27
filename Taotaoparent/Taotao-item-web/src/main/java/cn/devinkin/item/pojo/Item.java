package cn.devinkin.item.pojo;

import cn.devinkin.pojo.TbItem;

/**
 * @author devinkin
 * <p>Title: Item</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 15:20 2018/9/27
 */
public class Item extends TbItem {

    public Item(TbItem tbItem) {
        // 初始化属性
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages() {
        if (this.getImage() != null && !"".equals(this.getImage())) {
            String image = this.getImage();
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
