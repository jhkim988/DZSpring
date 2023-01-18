package com.dzspring.app.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dzspring.app.entity.GoodsImage;
import com.dzspring.app.repository.GoodsImageRepository;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class GoodsImageService {
	
	private final GoodsImageRepository goodsImageRepository;
	
	@Autowired
	public GoodsImageService(GoodsImageRepository goodsImageRepository) {
		this.goodsImageRepository = goodsImageRepository;
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
		return goodsImageRepository.store(multi);
	}
	
	public void printThumbnail(String stored_id, OutputStream out) {
		try {
			byte[] thumb = goodsImageRepository.getThumbnail(stored_id);
			out.write(thumb);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printFile(String stored_id, OutputStream out) {
		try (FileInputStream fis = new FileInputStream("C:\\upload\\" + stored_id);){
			byte[] buffer = new byte[4096];
			for (int read = fis.read(buffer); read > 0; read = fis.read(buffer)) {
				out.write(buffer, 0, read);
			}
		} catch (IOException e) {
			printFile("default", out);
		}
	}
}
