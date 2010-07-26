

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFileChooser;

public class C_FileIO{
	static JFileChooser fc;

	static String fileName;

	C_FileIO(){};

	public static boolean FileExists(String filename){
		try{
			new BufferedReader(new FileReader(new File(filename)));
		}catch(FileNotFoundException e){
			return false;
		}
		return true;
	}

	public static boolean OpenFile(ArrayList list, String _fileName, CW_Worldbuilder _window){
		if (fc==null && _window!=null){
			fc = new JFileChooser();
		}
		int returnVal=-1;
		if (_window!=null){
			returnVal = fc.showOpenDialog(_window);//REMEMBER TO CHANGE!
		}
		//WorldBuilder OpenFile
		if (_window!=null && returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();    
			try{
				BufferedReader br = new BufferedReader(new FileReader(file));

				CW_Worldbuilder.width=Integer.valueOf(br.readLine()).intValue();
				CW_Worldbuilder.height=Integer.valueOf(br.readLine()).intValue();
				CW_Worldbuilder.gridx=Integer.valueOf(br.readLine()).intValue();
				CW_Worldbuilder.gridy=Integer.valueOf(br.readLine()).intValue();
				CW_Worldbuilder.Editor.ChangeSize(CW_Worldbuilder.width,CW_Worldbuilder.height);
				String line;//  = new Strin();
				while((line = br.readLine())!=null){
					String temp = line.substring(0, 3);
					int entityID = Integer.valueOf(temp).intValue();
					int num, x, y, width, height, dir;
					boolean ismandatory;
					switch (entityID){
					case 100:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						if (C_Global.World.Player != null){
							C_Global.World.SetPlayerX(x);
							C_Global.World.SetPlayerY(y);
							list.add(C_Global.World.Player);
						}
						break;
					case 101:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_AI(x, y));
						break;
					case 150:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_Node(x, y));
						break;
					case 151:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_Node_MultiplayerSpawn(x, y));
						break;
					case 156:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_Pickup_Spawner(x, y,(short)155,true));
						break;
					case 154:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						int t = Integer.valueOf(line).intValue();
						if (t==1)
							list.add(new C_Flag(x, y,true));
						else
							list.add(new C_Flag(x, y,false));
						break;
					case 152:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						dir = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						int a = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						if (a==0)
							ismandatory = true;
						else
							ismandatory = false;	
						list.add(new C_Node_AI_Jump(dir, ismandatory, x,y,true));
						break;
					case 201:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						num = line.indexOf("|");
						list.add(new C_USP(x, y));
						break;
					case 250:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_M4(x, y));
						break;
					case 251:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_M8(x, y));
						break;
					case 252:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_FlameThrower(x, y));
						break;
					case 253:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_Sniper(x, y));
						break;
					case 102:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						width = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						height = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_Rectangle(true, x, y, width, height));
						break;
					}
				}
				br.close();
				fileName=file.getPath();
			}catch (IOException e){
				System.out.println("Read File Error");
				e.printStackTrace();
				return false;
			}
		}
		//Game OpenFile
		else if (_fileName!=""){
			File file = new File(_fileName);
			try{
				BufferedReader br = new BufferedReader(new FileReader(file));
				C_Global.World.CurrentLevel.width=Integer.valueOf(br.readLine()).intValue();
				C_Global.World.CurrentLevel.height=Integer.valueOf(br.readLine()).intValue();
				C_Global.World.CurrentLevel.gridx=Integer.valueOf(br.readLine()).intValue();
				C_Global.World.CurrentLevel.gridy=Integer.valueOf(br.readLine()).intValue();

				String line;//  = new Strin();
				while((line = br.readLine())!=null){
					String temp = line.substring(0, 3);
					int entityID = Integer.valueOf(temp).intValue();
					int num, x, y, width, height,dir;
					boolean ismandatory;
					switch (entityID){
					case 101:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_AI(x, y));
						break;
					case 150:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_Node(x, y, false));
						break;
					case 156:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_Pickup_Spawner(x, y,(short)155,false));
						break;
					case 151:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						C_Global.World.CurrentLevel.SpawnPoints[C_Global.World.CurrentLevel.Spawns]=new C_Node_MultiplayerSpawn(x, y);
						C_Global.World.CurrentLevel.Spawns++;
						break;
					case 154:
						if (C_Global.ctf){
							line=line.substring(3, line.length());
							num = line.indexOf("|");
							x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
							line = line.substring(num+1, line.length());
							num = line.indexOf("|");
							y = (int)Double.valueOf(line.substring(0, num)).doubleValue();

							line = line.substring(num+1, line.length());
							num = line.indexOf("|");
							int t = Integer.valueOf(line).intValue();
							if (t==1)
								list.add(new C_Flag(x, y,true));
							else
								list.add(new C_Flag(x, y,false));
						}
						break;
					case 201:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_USP(x, y));
						break;
					case 250:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_M4(x, y));
						break;
					case 251:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_M8(x, y));
						break;
					case 252:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_FlameThrower(x, y));
						break;
					case 253:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_Sniper(x, y));
						break;
					case 102:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						width = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						height = (int)Double.valueOf(line.substring(0, num)).doubleValue();
						list.add(new C_Rectangle(true, x, y, width, height));
						break;
					case 152:
						line=line.substring(3, line.length());
						num = line.indexOf("|");
						x = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						y = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						dir = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						line = line.substring(num+1, line.length());
						num = line.indexOf("|");
						int a = (int)Double.valueOf(line.substring(0, num)).doubleValue();

						if (a==0)
							ismandatory = true;
						else
							ismandatory = false;	
						list.add(new C_Node_AI_Jump(dir, ismandatory, x,y,false));
						break;
					}
				}
				br.close();
				fileName=file.getPath();
			}catch (IOException e){
				System.out.println("Read File Error");
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static boolean SaveFile(ArrayList list, CW_Worldbuilder _window){	
		if (fileName==null || fileName.equals("")){
			if (fc==null){
				fc = new JFileChooser();
			}
			int returnVal = fc.showSaveDialog(_window);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				return WriteFile(list,file);
			}
		}
		else{
			File file = new File(fileName);
			return WriteFile(list,file);
		}
		return false;
	}

	public static boolean SaveFileAs(ArrayList _list, CW_Worldbuilder _window){
		if (fc==null){
			fc = new JFileChooser();
		}
		int returnVal = fc.showSaveDialog(_window);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			return WriteFile(_list,file);
		}
		return true;
	}	

	public static boolean WriteFile(ArrayList list,File file){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			Iterator iter = list.iterator();

			bw.write(""+CW_Worldbuilder.width);
			bw.newLine();
			bw.write(""+CW_Worldbuilder.height);
			bw.newLine();
			bw.write(""+CW_Worldbuilder.gridx);
			bw.newLine();
			bw.write(""+CW_Worldbuilder.gridy);
			bw.newLine();	

			while(iter.hasNext()){
				C_Entity Object = (C_Entity)iter.next();
				bw.write(""+Object.entityID,0,3);
				switch (Object.entityID){
				case 154:
					C_Flag temp = (C_Flag)Object;
					if (temp.red==true)
						bw.write(""+Object.x+'|'+Object.y+"|1");
					else
						bw.write(""+Object.x+'|'+Object.y+"|0");
					break;
				case 102:
					bw.write(""+Object.x+'|'+Object.y+'|'+Object.width+'|'+Object.height);
					break;
				case 152:
					C_Node_AI_Jump Node = (C_Node_AI_Jump)Object;
					bw.write(""+Node.x+'|'+Node.y+'|'+Node.dir+'|'+(int)(Node.ismandatory ? 0 : 1));
					break;
				default :
					bw.write(""+Object.x+'|'+Object.y);
				break;
				}
				bw.newLine();
			}
			bw.close();
			fileName=file.getPath();
		}catch (IOException e){
			System.out.println("Write File Error");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}