package EdgeDetection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		Scanner one = new Scanner(System.in);
		System.out.println("Introduceti numele pozei (fara extensie): ");
		String numeSrc = one.next();
		String sursa = "C:\\Users\\Tom\\Desktop\\poze\\Original\\" + numeSrc + ".bmp";
		try {
			long time1 = System.currentTimeMillis();
			bA poza = new byteArray(sursa); // se citeste fisierul cu poza
			eD pozaBitMap = new bitmap(poza); // se decodeaza fisierul
			long time2 = System.currentTimeMillis();
			pozaBitMap.grayscale(poza); // se aplica grayscale
			pozaBitMap.edgeDetection(poza); // se gaseesc marginile cu operatorul Sobel
			long time3 = System.currentTimeMillis();

			System.out.println("Introduceti numele pozei destinatie (fara extensie): ");
			String numeDest = one.next();
			String destinatie = "C:\\Users\\Tom\\Desktop\\poze\\EdgeDetection\\" + numeDest + ".bmp";
			long time4 = System.currentTimeMillis();
			poza.writeBitmap(destinatie); // se salveaza rezultatul
			long time5 = System.currentTimeMillis();

			System.out.println("Poza modificata cu success!");
			System.out.print("Timp citire: ");
			System.out.print(time2 - time1);
			System.out.print(" ms \nTimp editare: ");
			System.out.print(time3 - time2);
			System.out.print(" ms \nTimp scriere: ");
			System.out.print(time5 - time4);
			System.out.println(" ms");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		one.close();

	}
}
