package EdgeDetection;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface bA extends filtru {
	public void writeBitmap(String file) throws FileNotFoundException, IOException;

	public byte[] getRawData();

	public int getLength();

	public void setRawDataAtIndex(int index, byte value);
}
