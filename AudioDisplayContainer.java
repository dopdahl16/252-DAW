
    	
    	File current_file = getTracksList().get(getCurrentTrack());
    	File write_file = new File("C:\\Users\\dopda\\Desktop\\DAW WAV Files\\" + "1" + getTracksList().get(getCurrentTrack()).getName());
    	
    	FileOutputStream out = null;
    	FileInputStream in = null;
    	
    	
		try {
			in = new FileInputStream(current_file);
			out = new FileOutputStream(write_file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		try {
			for (int i = 0; i<(current_file.length()/2); i++) {
				
				while (i < 44) {
					byte b1 = (byte) (in.read() & 0xff);
					byte b2 = (byte) (in.read() & 0xff);
					System.out.println(b1 + " " + b2 + " ");
					out.write(b1);
					out.write(b2);
					System.out.println(b1 + " " + b2 + " ");
					System.out.println(i);
					i++;
				}
				
				
			byte b1 = (byte) (in.read() & 0xff);
			byte b2 = (byte) (in.read() & 0xff);
			
			short s = ((short) (b2 << 8 | (((int) b1) & 0xff) & 0xffff));
			
				s = (short) (s * scaling_ratio);
				byte b3 = ((byte) (s & 0xff));
				byte b4 = ((byte) (s >> 8 & 0xff));
				if (b1 * scaling_ratio >= 127 || b1 * scaling_ratio <= -128) {
					if (b1 > 0) {
						b3 = 127;
					}
					if (b1 < 0) {
						b3 = -128;
					}
				}
				if (b2 * scaling_ratio >= 127 || b2 * scaling_ratio <= -128) {
					if (b2 > 0) {
						b4 = 127;
					}
					if (b2 < 0) {
						b4 = -128;
					}
				}
			
				out.write(b3);
				out.write(b4);
			
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    
