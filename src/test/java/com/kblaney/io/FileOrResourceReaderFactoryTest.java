package com.kblaney.io;

import static org.junit.Assert.*;
import java.io.File;
import java.io.Reader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public final class FileOrResourceReaderFactoryTest
{
  @Test(expected = IllegalArgumentException.class)
  public void constructor_NullFileName()
  {
    new FileOrResourceReaderFactory(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getInstance_NeitherFileNorResourceExists() throws Exception
  {
    final String fileName = "No file or resource exists with this name";
    final ReaderFactory factory = new FileOrResourceReaderFactory(fileName);
    factory.getInstance();
  }

  @Test
  public void getInstance_OnlyFileExists() throws Exception
  {
    final String fileName = "file.txt";
    final File srcFile = new File(getClass().getResource("/" + fileName).toURI());
    final File destDir = new File(System.getProperty("user.dir"));
    FileUtils.copyFileToDirectory(srcFile, destDir);
    try
    {
      final ReaderFactory factory = new FileOrResourceReaderFactory(fileName);
      final Reader reader = factory.getInstance();
      try
      {
        assertEquals("file-contents", IOUtils.toString(reader));
      }
      finally
      {
        IOUtils.closeQuietly(reader);
      }
    }
    finally
    {
      new File(destDir, fileName).delete();
    }
  }

  @Test
  public void getInstance_OnlyResourceExists() throws Exception
  {
    final ReaderFactory factory = new FileOrResourceReaderFactory("resource-file.txt");
    final Reader reader = factory.getInstance();
    try
    {
      assertEquals("resource-file-contents", IOUtils.toString(reader));
    }
    finally
    {
      IOUtils.closeQuietly(reader);
    }
  }
}
