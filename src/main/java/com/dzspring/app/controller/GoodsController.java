package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dzspring.app.entity.Goods;
import com.dzspring.app.entity.GoodsImage;
import com.dzspring.app.service.GoodsService;
import com.dzspring.app.service.impl.GoodsImageService;
import com.dzspring.app.service.impl.GoodsSearchService;

@RestController
@RequestMapping("/goods")
public class GoodsController {

	private final GoodsService goodsService;
	private final GoodsSearchService goodsSearchService;
	private final GoodsImageService goodsImageService;
	
	@Autowired	
	public GoodsController(GoodsService goodsService, GoodsSearchService goodsSearchService, GoodsImageService goodsImageService) {
		this.goodsService = goodsService;
		this.goodsSearchService = goodsSearchService;
		this.goodsImageService = goodsImageService;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<ResponseMessage> insert(@RequestBody Goods goods) {
		boolean result = goodsService.insert(goods);
		ResponseMessage message = new ResponseMessage();
		if (result) {
			message.setData(true);
			message.setMessage("상품이 등록됐습니다.");
			message.setUrl(ResponseMessage.path("/admin/goods"));
		} else {
			message.setData(false);
			message.setMessage("일시적 오류");
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
			message.setUrl(ResponseMessage.path("/admin/goods"));
		} else {
			message.setData(false);
			message.setMessage("일시적 오류");
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
	
	@RequestMapping("/search")
	public ResponseEntity<ResponseMessage> search(@RequestBody HashMap<String, Object> searchInfo) {
		ResponseMessage message = new ResponseMessage();
		message.setData(goodsSearchService.invoke(searchInfo));
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping("/image")
	public ResponseEntity<ResponseMessage> update(MultipartHttpServletRequest request) {
		List<String> stored_names = new ArrayList<>();
		Iterable<String> iter = () -> request.getFileNames();
		StreamSupport.stream(iter.spliterator(), false).forEach(org_name -> {
			MultipartFile multi = request.getFile(org_name);
			File image = goodsImageService.store(multi);
			goodsImageService.insertThumbnail(image);
			stored_names.add(image.getName());
			goodsImageService.insert(GoodsImage.builder()
					.org_name(org_name)
					.stored_name(image.getName())
					.content_type(multi.getContentType())
					.content_length(multi.getSize())
					.build());
		});
		ResponseMessage message = new ResponseMessage();
		message.setData(stored_names);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/thumbnail/{stored_id}", method=RequestMethod.GET)
	public void thumbnail(@PathVariable String stored_id, HttpServletResponse response) {
		try (OutputStream out = response.getOutputStream();) {
			goodsImageService.printThumbnail(stored_id, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
