package jp.smaphonia.app.photo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.smaphonia.domain.model.Photo;
import jp.smaphonia.domain.service.photo.PhotoService;

@Controller
@RequestMapping("/photo")
public class PhotoController {
	
	@Autowired
	PhotoService photoService;

	@RequestMapping(method=RequestMethod.GET)
	ResponseEntity<byte[]> getPhoto(String id) {
		if (id == null) {
			byte[] bytes = new byte[0];
			return new ResponseEntity<byte[]>(bytes, HttpStatus.CREATED);
		}
		Photo photo = photoService.findPhoto(id);
		return new ResponseEntity<byte[]>(photo.getData(), HttpStatus.CREATED);
	}
}
