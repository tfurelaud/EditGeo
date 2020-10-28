package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Used to serialize plans
 * @author Cayrol, Furelaud
 *
 */
public class Serializer {
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public Serializer() {
	}
	
	public void autosave(Plan p) {
		try {
			final FileOutputStream fichier = new FileOutputStream("src/save/autosave.ser");
		      oos = new ObjectOutputStream(fichier);
			  oos.writeObject(p);
		      oos.flush();
		    } catch (final IOException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        if (oos != null) {
		          oos.flush();
		          oos.close();
		        }
		      } catch (final IOException ex) {
		        ex.printStackTrace();
		      }
		    }
	}
	
	public void save(Plan p, File file) {

		try {
		      FileOutputStream fos = new FileOutputStream(file,true);
		      oos = new ObjectOutputStream(fos);
			  oos.writeObject(p);
		      oos.flush();
		    } catch (final IOException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        if (oos != null) {
		          oos.flush();
		          oos.close();
		        }
		      } catch (final IOException ex) {
		        ex.printStackTrace();
		      }
		    }
	}
	
	public Plan load(ObjectInputStream ois) {
		Plan p =null;
		try {
		     p = (Plan) ois.readObject();
		} catch (final java.io.IOException e) {
		     e.printStackTrace();
		} catch (final ClassNotFoundException e) {
		     e.printStackTrace();
		}
		return p;
	}
	
	public Plan autoload(Plan p) {
		p =null;
		try {
			
			  final FileInputStream fichier = new FileInputStream("src/save/autosave.ser");
		      ois = new ObjectInputStream(fichier);
		      p = (Plan) ois.readObject();

		    } catch (final java.io.IOException e) {
		      e.printStackTrace();
		    } catch (final ClassNotFoundException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        if (ois != null) {
		          ois.close();
		        }
		      } catch (final IOException ex) {
		        ex.printStackTrace();
		      }
		    }
		return p;
	}
}
