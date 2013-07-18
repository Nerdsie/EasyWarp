package com.nerdswbnerds.easywarp.managers;

import com.nerdswbnerds.easywarp.objects.Warp;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
	public static void loadWarps() {
		String fName = "Warps.data";

		File file = new File("plugins/Easy Warp/" + fName);

		if (file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
				Object result = ois.readObject();

				ois.close();
				if (result != null) {
					@SuppressWarnings("unchecked")
					ArrayList<String> parse = (ArrayList<String>) result;

					for (String i : parse) {
						try {
							WarpManager.addWarp(WarpManager.parse(i));

							ois.close();
						} catch (Exception e) {
							System.out.println("Easy Warp had an error loading warps.");
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Easy Warp had an error loading warps.");
			}
		}
	}

	public static void saveWarps() {
		String fName = "Warps.data";

		ArrayList<String> format = new ArrayList<String>();

		for (Warp w : WarpManager.getWarps()) {
			format.add(w.toString());
		}

		File file = new File("plugins/Easy Warp/" + fName);

		new File("plugins/").mkdir();
		new File("plugins/Easy Warp/").mkdir();

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Easy Warp had an error saving warps.");
			}
		}

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
			oos.writeObject(format);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			System.out.println("Easy Warp had an error saving warps.");
		}
	}

}
