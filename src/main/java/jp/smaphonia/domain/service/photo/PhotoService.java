package jp.smaphonia.domain.service.photo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.smaphonia.domain.model.Photo;
import jp.smaphonia.domain.repository.photo.PhotoRepository;

@Service
public class PhotoService {

	@Autowired
	PhotoRepository photoRepository;
	
	public Photo findPhoto(String id) {
		return photoRepository.findOne(id);
	}
	public Photo newPhoto() {
		String maxId = photoRepository.getMaxId();
		int index = Integer.parseInt(maxId.substring("P".length())) + 1;
		String newId = "P" + String.format("%05d", index);
		Photo photo = new Photo();
		photo.setId(newId);
		return photo;
	}
	public String update(String id, byte[] data) {
		Photo photo = photoRepository.findOne(id);
		if (photo == null) {
			photo = new Photo();
			photo.setId(id);
		}
		if (data.length > 0) {
			photo.setData(data);
		}
		photoRepository.save(photo);
		
		return photo.getId();
	}
	
	public void cancel(String id) {
		Photo photo = photoRepository.findOne(id);
		photoRepository.delete(photo);
	}
	
}
