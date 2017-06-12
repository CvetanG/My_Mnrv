package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Date;

import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;

public class MyDropbox {
	
	private static void getFile(DbxClientV2 dbxClient, File localFile, String dropboxPath) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = dbxClient.files().download(dropboxPath).getInputStream();
		
		outputStream = new FileOutputStream(localFile);

		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		System.out.println("Done getting file!");

	}
	
	private static void uploadFile(DbxClientV2 dbxClient, File localFile, String dropboxPath) {
        try (InputStream in = new FileInputStream(localFile)) {
            FileMetadata metadata = dbxClient.files().uploadBuilder(dropboxPath)
                .withMode(WriteMode.ADD) // to change the mode to update
                .withClientModified(new Date(localFile.lastModified()))
                .uploadAndFinish(in);

            System.out.println(metadata.toStringMultiline());
        } catch (UploadErrorException ex) {
            System.err.println("Error uploading to Dropbox: " + ex.getMessage());
            System.exit(1);
        } catch (DbxException ex) {
            System.err.println("Error uploading to Dropbox: " + ex.getMessage());
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Error reading from file \"" + localFile + "\": " + ex.getMessage());
            System.exit(1);
        }
        System.out.println("Done uploading file!");
	}
	
	// Get files and folder metadata from Dropbox root directory
	private static void getRootDir(DbxClientV2 client) throws ListFolderErrorException, DbxException {
	    ListFolderResult result = client.files().listFolder("");
	    while (true) {
	        for (Metadata metadata : result.getEntries()) {
	            System.out.println(metadata.getPathLower());
	        }
	
	        if (!result.getHasMore()) {
	            break;
	        }
	
	        result = client.files().listFolderContinue(result.getCursor());
	    }
	}
	
	
	public static void main(String[] args) throws IOException, DbxException {
		String localPath = "temp.txt";
		
		File localFile = new File(localPath);
		
		String dropboxPath = "/Finance/" + localPath;
		
    	String argAuthFileOutput = "authFile.app";
        
        DbxAuthInfo authInfo;
        try {
            authInfo = DbxAuthInfo.Reader.readFromFile(argAuthFileOutput);
        } catch (JsonReader.FileLoadException ex) {
            System.err.println("Error loading <auth-file>: " + ex.getMessage());
            System.exit(1);
            return;
        }
        
        // Create a DbxClientV2, which is what you use to make API calls.
        DbxRequestConfig requestConfig = new DbxRequestConfig("examples-upload-file");
        DbxClientV2 client = new DbxClientV2(requestConfig, authInfo.getAccessToken(), authInfo.getHost());

//        System.out.println("Linked account: " + client.users().getCurrentAccount());
        
     
        getRootDir(client);
//        uploadFile(client, localFile, dropboxPath);
//        getFile(client, localFile, dropboxPath);
        
    }
}