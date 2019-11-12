package EdgeDetection;
public class bitmap implements eD {

	int width;
	int height;
	int offset;
	int gap;
	pixel[][] imagine;

	public bitmap(bA poza) { // scoate informatiile relevante din header
		offset = byte2int(0xA, poza);
		width = byte2int(0x12, poza);
		height = byte2int(0x16, poza);
		gap = ((poza.getLength() - offset) / height) - (3 * width);

		imagine = new pixel[height][width]; // construieste matricea de pixeli
		for (int lin = 0; lin < height; lin++)
			for (int col = 0; col < width; col++)
				imagine[lin][col] = byte2pixel(lin, col, poza);
	}

	private int byte2int(int address, bA poza) { // transforma 4 octeti din rawdata in int
		byte[] block = new byte[4];
		for (int i = 0; i < 4; i++)
			block[i] = poza.getRawData()[address + i];
		int num = 0;
		for (int i = 0; i < 4; i++)
			num += (long) (block[i] & 0xFFl) << 8 * i;
		return num;
	}

	private pixel byte2pixel(int lin, int col, bA poza) // transforma 3 octeti din rawdata intr-un pixel
	{
		byte[] block = new byte[3];
		int local_offset = (lin * width + col) * 3; // numarul de octeti parcursi pana acum
		int global_gap = lin * gap; // numarul de octeti ocupati de gap-ul de la marginea liniilor
		int total_offset = global_gap + local_offset + offset;
		for (int k = 0; k < 3; k++)
			block[k] = poza.getRawData()[total_offset + k];
		int blue = (block[0] & 0xff);
		int green = (block[1] & 0xff);
		int red = (block[2] & 0xff);
		pixel pix = new pixel(red, green, blue);
		return pix;
	}

	public void grayscale(bA poza) { // transforma poza in grayscale utilizand metoda mediei
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				imagine[i][j].r = (imagine[i][j].r + imagine[i][j].b + imagine[i][j].g) / 3;
				imagine[i][j].b = imagine[i][j].r;
				imagine[i][j].g = imagine[i][j].r;
			}
		refreshData(poza);
	}

	public void edgeDetection(bA poza) { // gaseste marginile din poza cu operatorul Sobel
		int k = 0;
		int[] newData = new int[width * height];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				if (i == 0 || i == height - 1 || j == 0 || j == width - 1)
					newData[k++] = imagine[i][j].r;
				else {
					int gy = imagine[i + 1][j - 1].r + 2 * imagine[i + 1][j].r + imagine[i + 1][j + 1].r
							- imagine[i - 1][j - 1].r - 2 * imagine[i - 1][j].r - imagine[i - 1][j + 1].r;
					int gx = imagine[i - 1][j - 1].r + 2 * imagine[i][j - 1].r + imagine[i + 1][j - 1].r
							- imagine[i - 1][j + 1].r - 2 * imagine[i][j + 1].r - imagine[i + 1][j + 1].r;
					newData[k++] = (int) Math.sqrt(gx * gx + gy * gy);
				}

			}
		k = 0;
		for (int i = 0; i < height; i++) // inlocuieste valorile calculate
			for (int j = 0; j < width; j++) {
				imagine[i][j].r = newData[k];
				imagine[i][j].b = newData[k];
				imagine[i][j].g = newData[k++];
			}
		refreshData(poza);
	}

	private void refreshData(bA poza) { // modifica rawdata cu noile valori aflate in imagine
		int k = 0;
		for (int lin = 0; lin < height; lin++) {
			for (int col = 0; col < width; col++) {
				poza.setRawDataAtIndex(offset + k++, (byte) imagine[lin][col].b);
				poza.setRawDataAtIndex(offset + k++, (byte) imagine[lin][col].g);
				poza.setRawDataAtIndex(offset + k++, (byte) imagine[lin][col].r);
			}
			k += gap;
		}
	}

}
