package com.dev.practise.ssh.session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class JschSession {
	
	public static void main(String[] args) {
		
		String user = "user";
		String pass = "pass";
		String host = "10.10.10.10";
		int port = 22;
		String remoteFile = "/home/user/sample/abc.txt";
		
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setPassword(pass);
			
			session.connect();
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			
			InputStream inputStream = sftpChannel.get(remoteFile);
			
			
			ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
			BufferedReader in = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
			channelExec.setCommand(
					"sudo docker run -v /home/user/image_name:tag run_calc params");
			
			channelExec.connect();
			sftpChannel.disconnect();
			channelExec.disconnect();
			session.disconnect();
		} catch(Exception ex) {
			ex.printStackTrace();
		} 
		
	}

}
