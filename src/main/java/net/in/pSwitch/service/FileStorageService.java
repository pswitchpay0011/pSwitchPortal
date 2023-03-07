package net.in.pSwitch.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import net.in.pSwitch.model.FileDB;

public interface FileStorageService {

	FileDB store(MultipartFile file) throws IOException;

	FileDB getFile(String id);

	void deleteFile(String id);

	Stream<FileDB> getAllFiles();
}
