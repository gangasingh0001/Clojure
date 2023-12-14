# Clojure Compression and Decompression

## Overview
This Clojure project provides functionality for compressing and decompressing text files using a custom frequency-based algorithm. The implementation involves parsing, processing, and transforming text files while maintaining a frequency map for efficient compression and decompression.

## Modules

### 1. `compress`
- **Description:** Handles compression of text files.
- **Functions:**
  - `compress-file`: Compresses a given text file using a custom frequency-based algorithm.
  - `decompress-text`: Decompresses a previously compressed text file.
  - `display-file-contents`: Displays the contents of a specified file.
  - `list-files`: Lists all files in the current directory.

### 2. `menu`
- **Description:** Provides a simple interactive menu for users to choose compression or decompression operations.
- **Functions:**
  - `menu`: Main menu loop for user interaction.

## Execution

1. Clone the repository.
2. Ensure you have a working Clojure environment set up.
3. Run the `menu` namespace to interactively choose compression or decompression operations.

```clojure
(menu)
```

Follow the prompts to list files, display file contents, compress a file, decompress a file, or exit.
Usage

Choose option 1 to list files in the current directory.
Choose option 2 to display the contents of a specified file.
Choose option 3 to compress a file.
Choose option 4 to decompress a previously compressed file.
Choose option 5 to exit the menu.
Notes

The compression algorithm utilizes a frequency map stored in the frequency.txt file.
Compressed files are saved with a .ct extension.
The project aims to demonstrate basic file manipulation and custom compression techniques in Clojure.
Feel free to explore and experiment with different text files for compression and decompression.

Disclaimer: This project is a simplified illustration and may not be suitable for general-purpose compression/decompression needs.
