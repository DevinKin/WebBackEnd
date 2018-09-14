package cn.devinkin.service;

import java.util.List;

import cn.devinkin.pojo.Items;
import org.springframework.stereotype.Service;

public interface ItemsService {

	public List<Items> list() throws Exception;
	
	public Items findItemsById(Integer id) throws Exception;

	public void updateItems(Items items) throws Exception;
}
