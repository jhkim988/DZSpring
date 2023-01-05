package com.dzspring.app.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dzspring.app.entity.GoodsImage;
import com.dzspring.app.repository.GoodsImageRepository;
import com.dzspring.app.repository.GoodsRepository;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class GoodsImageService {
	
	private final GoodsImageRepository goodsImageRepository;
	private final GoodsRepository goodsRepository;
	
	@Autowired
	public GoodsImageService(GoodsImageRepository goodsImageRepository, GoodsRepository goodsRepository) {
		this.goodsImageRepository = goodsImageRepository;
		this.goodsRepository = goodsRepository;
	}
	
	public File insertThumbnail(File image) {
		File thumbnail = new File("c:\\upload\\thumbnail\\" + image.getName());
		try {
			Thumbnails.of(image).size(100, 100).outputFormat("png").toFile(thumbnail);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return thumbnail;
	}
	
	public boolean insert(GoodsImage goodsImages) {
		return 1 == goodsImageRepository.insert(goodsImages);
	}
	
	public File store(MultipartFile multi) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		String stored_name = System.nanoTime() + Integer.toHexString(rand.nextInt());
		File image = new File("C:\\upload\\"+stored_name);
		try {
			multi.transferTo(image);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return null;
		}
		return image;
	}
	
	public void printThumbnail(String stored_id, OutputStream out) {
		File image = null;
		try {
			image = new File("C:\\upload\\thumbnail\\" + stored_id + ".png");
			Thumbnails.of(image).size(100, 100).outputFormat("png").toOutputStream(out);
			byte[] buffer = new byte[4096];
			out.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
