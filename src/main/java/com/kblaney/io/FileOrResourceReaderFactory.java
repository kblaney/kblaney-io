package com.kblaney.io;

import java.io.FileNotFoundException;
import com.kblaney.assertions.ArgAssert;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.commons.io.Charsets;

/**
 * Provides {@code Reader} instances that either read from a specified file in the current working directory or from a
 * resource file in the default package (if a file with the specified name does not exist in the current working
 * directory).
 */
public final class FileOrResourceReaderFactory implements ReaderFactory
{
  private final String fileName;

  /**
   * Constructs a new instance that either reads from a specified file in the current working directory or from a
   * resource file in the default package (if a file with the specified name does not exist in the current working
   * directory).  Note that in either case, the file's content is assumed to be encoded in UTF-8. 
   * 
   * For example, if {@code fileName} is {@code "foo"}, this class first looks for a file named {@code "foo"} in the
   * current working directory. If that file exists, this class' {@link #getInstance} method returns a reader that reads
   * from the file. If no such file exists, this class' {@link #getInstance} method returns a reader that reads from a
   * resource file named {@code foo} in the default package.
   * 
   * @param fileName the file name, which can't be null
   */
  public FileOrResourceReaderFactory(final String fileName)
  {
    this.fileName = ArgAssert.assertNotNull(fileName, "fileName");
  }

  /** {@inheritDoc} */
  public Reader getInstance() throws IOException
  {
    return new InputStreamReader(getInputStream(), Charsets.UTF_8);
  }

  private InputStream getInputStream() throws FileNotFoundException
  {
    final File file = new File(fileName);
    if (file.isFile())
    {
      return new FileInputStream(file);
    }
    else
    {
      return getInputStreamFromResource();
    }
  }

  private InputStream getInputStreamFromResource()
  {
    final String resourceName = "/" + fileName;
    final InputStream inputStream = getClass().getResourceAsStream(resourceName);
    if (inputStream == null)
    {
      throw new IllegalArgumentException("Neither file nor resource exists: " + fileName);
    }
    return inputStream;
  }
}
