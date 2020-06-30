package com.songtradr.metadata.service;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.songtradr.metadata.pojo.METADATA;
import com.songtradr.metadata.pojo.Track;

public class XMLFileReader {

	public static METADATA readXmlFile() {

		METADATA metaData = null;
		try {

			File inputFile = new File("D:\\SongAutomate\\SongRadrAutomated\\src\\main\\resources\\xml\\10691.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(METADATA.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			metaData = (METADATA) jaxbUnmarshaller.unmarshal(inputFile);

			Track[] trackObject = metaData.getProduct().getTrack();
			for (int i = 0; i < trackObject.length; i++) {
				System.out.println(trackObject[i]);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return metaData;
	}

	public static void main(String[] args) {
		readXmlFile();
	}
}
