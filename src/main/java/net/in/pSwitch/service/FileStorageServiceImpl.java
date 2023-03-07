package net.in.pSwitch.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.in.pSwitch.model.FileDB;
import net.in.pSwitch.repository.FileDBRepository;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	@Autowired
	private FileDBRepository fileDBRepository;

	@Override
	public FileDB store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

		return fileDBRepository.save(FileDB);
	}

	@Override
	public FileDB getFile(String id) {
		return fileDBRepository.findById(id).get();
	}

	@Override
	public Stream<FileDB> getAllFiles() {
		return fileDBRepository.findAll().stream();
	}

	@Override
	public void deleteFile(String id) {
		fileDBRepository.deleteById(id);
	}
}