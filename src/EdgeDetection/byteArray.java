package EdgeDetection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class byteArray implements bA {

	byte[] rawdata;
	int length;

	public byteArray(String file) { // citeste fisierul

		try {
			InputStream inputStream = new FileInputStream(file);
			length = (int) new File(file).length();
			rawdata = new byte[length];
			inputStream.read(rawdata);
			inputStream.close();
		} catch (IOException e) {
			System.out.println("Eroare de citire a fisierului " + file);
			System.out.println("Nu exista fisierul sau nu se poatea accesa");
			System.exit(1);
		}
	}

	public void writeBitmap(String file) throws FileNotFoundException, IOException {
		try (OutputStream outputStream = new FileOutputStream(file);) {
			outputStream.write(this.rawdata);
		} catch (IOException ex) {
			System.out.println("Nu s-a putut scrie fisierul " + file);
			System.exit(2);
		}
	}
	
	public byte[] subString64(int poz) {
		byte[] subStr = new byte[1];
		if (poz > length) {
			return subStr;
		}
		if (poz + 63 < length) {
			subStr = new byte[63];
			for (int i=0;i<63;i++)
				subStr[i] = rawdata[i+poz];
		}
		else {
			subStr = new byte[length-poz];
			for (int i=0;i<subStr.length;i++)
				subStr[i] = rawdata[i+poz];
		}
		return subStr;
	}
	
	public byte[] subString126(int poz) {
		byte[] subStr = new byte[1];
		if (poz > length) {
			return subStr;
		}
		if (poz + 126 < length) {
			subStr = new byte[126];
			for (int i=0;i<126;i++)
				subStr[i] = rawdata[i+poz];
		}
		else {
			subStr = new byte[length-poz];
			for (int i=0;i<subStr.length;i++)
				subStr[i] = rawdata[i+poz];
		}
		return subStr;
	}

	@Override
	public byte[] getRawData() {
		return rawdata;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public void setRawDataAtIndex(int index, byte value) { // modifica valoarea rawdata pentru a putea fi scris in
															// fisier
		rawdata[index] = value;
	}

}
