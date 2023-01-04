package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Goods;
import com.dzspring.app.service.GoodsService;

@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<ResponseMessage> insert(@RequestBody Goods goods) {
		boolean result = goodsService.insert(goods);
		ResponseMessage message = new ResponseMessage();
		if (result) {
			message.setData(true);
			message.setMessage("상품이 등록됐습니다.");
			message.setUrl("");
		} else {
			message.setData(false);
			message.setMessage("일시적 오류");
			message.setUrl("");
		}
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> update(@RequestBody Goods goods) {
		boolean result = goodsService.update(goods);
		ResponseMessage message = new ResponseMessage();
		if (result) {
			message.setData(true);
			message.setMessage("상품이 수정됐습니다.");
			message.setUrl("");
		} else {
			message.setData(false);
			message.setMessage("일시적 오류");
			message.setUrl("");
		}
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> update(@PathVariable int id) {
		boolean result = goodsService.delete(id);
		ResponseMessage message = new ResponseMessage();
		if (result) {
			message.setData(true);
			message.setMessage("상품이 삭제됐습니다.");
			message.setUrl("");
		} else {
			message.setData(true);
			message.setMessage("일시적 오류");
			message.setUrl("");
		}
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
}
