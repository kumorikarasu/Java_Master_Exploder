import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.FileReader;
import java.io.IOException;

public class C_SaveOptions {
	static void Write(){
		File Save = new File("settings.cfg");
		FileOutputStream Bf;
		try {
			Bf = new FileOutputStream(Save);
			byte[] buffer = new byte[512];
			buffer = C_Global.World.Player.Nickname.getBytes();

		//	System.out.println(buffer.toString());
			Bf.write(buffer);
			Bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	static String Open(){
		File Save = new File("settings.cfg");
		try {
			BufferedReader Bf = new BufferedReader(new FileReader(Save));
			String line = Bf.readLine();
			//System.out.println(buffer);
			Bf.close();
			return line;
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unnamed";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unnamed";
		}
	}
}
