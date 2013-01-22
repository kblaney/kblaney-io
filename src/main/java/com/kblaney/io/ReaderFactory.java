package com.kblaney.io;

import java.io.IOException;
import java.io.Reader;

/**
 * Returns reader instances.
 */
public interface ReaderFactory
{
  /**
   * Gets a reader instance.
   * 
   * @return the instance
   * 
   * @throws IOException if can't get the instance
   */
  Reader getInstance() throws IOException;
}
