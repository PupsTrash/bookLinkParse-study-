package com.flibusta;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public interface IDownloadFile {

    File downloadFileTo(String linkToDownload) throws IOException;
}
