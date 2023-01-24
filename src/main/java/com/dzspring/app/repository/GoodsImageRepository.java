package com.dzspring.app.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.dzspring.app.entity.GoodsImage;
import com.dzspring.app.service.impl.LRUCache;

@Repository
public class GoodsImageRepository {
	private final GoodsImageMybatisRepository goodsImageMybatisRepository;
	private final LRUCache<byte[]> cache;

	@Autowired
	GoodsImageRepository(GoodsImageMybatisRepository goodsImageMybatisRepository, LRUCache<byte[]> cache) {
		this.goodsImageMybatisRepository = goodsImageMybatisRepository;
		this.cache = cache;
	}

	public int insert(GoodsImage goodsImage) {
		return goodsImageMybatisRepository.insert(goodsImage);
	}

	public int updateGoodsId(Map<String, Object> map) {
		return goodsImageMybatisRepository.updateGoodsId(map);
	}

	public GoodsImage findOneByStoredName(String stored_name) {
		return goodsImageMybatisRepository.findOneByStoredName(stored_name);
	}

	public File store(MultipartFile multi) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		String stored_name = System.nanoTime() + Integer.toHexString(rand.nextInt());
		File image = new File("C:\\upload\\" + stored_name);
		try {
			multi.transferTo(image);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return null;
		}
		return image;
	}

	public byte[] getThumbnail(String stored_id) {
		byte[] thumb = new byte[0];
		if (cache.containsKey(stored_id)) {
			thumb = cache.get(stored_id);
		} else {
			File image = new File("C:\\upload\\thumbnail\\" + stored_id + ".png");
			try (FileInputStream fis = new FileInputStream(image)) {
				thumb = new byte[(int) image.length()];
				fis.read(thumb);
				cache.add(stored_id, thumb);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return thumb;
	}
}
