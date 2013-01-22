package com.kblaney.io;

import com.kblaney.assertions.ArgAssert;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Provides {@code Reader} instances that either read from a specified file in the current working directory or from a
 * resource file in the default package (if a file with the specified name does not exist in the current working
 * directory).
 * 
 * <p>
 * For example, if this class' constructor is passed a file name of "foo", this class first looks for a file named "foo"
 * in the current working directory. If that file exists, this class' {@link #getInstance} method returns a reader that
 * reads from the file. If no such file exists, this class' {@link #getInstance} method returns a reader that reads from
 * a resource file named "foo" in the default package.
 * </p>
 */
public final class FileOrResourceReaderFactory implements ReaderFactory
{
  private final File file;
  private final String resourceName;

  /**
   * Constructs a new instance.
   * 
   * @param fileName the file name, which can't be null
   */
  public FileOrResourceReaderFactory(final String fileName)
  {
    ArgAssert.assertNotNull(fileName, "fileName");

    file = new File(fileName);
    resourceName = "/" + fileName;
  }

  /** {@inheritDoc} */
  public Reader getInstance() throws IOException
  {
    if (file.isFile())
    {
      return new FileReader(file);
    }
    else
    {
      return new InputStreamReader(getClass().getResourceAsStream(resourceName));
    }
  }
}
