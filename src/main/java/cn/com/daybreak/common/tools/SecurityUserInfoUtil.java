package cn.com.daybreak.common.tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUserInfoUtil {
	private static UserDetails userDetails;
	
	private static void getUserDetails(){
		try{
			userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e){
			userDetails = null;
		}
	}
	
	public static boolean isLogin(){
		getUserDetails();
		
		return null!=userDetails;
	}
	
	public static String getUsername(){
		getUserDetails();
		
		if(null!=userDetails){
			return userDetails.getUsername();
		}else{
			return null;
		}
	}
	
	public static String getPassword(){
		getUserDetails();
		
		if(null!=userDetails){
			return userDetails.getPassword();
		}else{
			return null;
		}
	}
	
	public static Set<String> getAuthorities(){
		getUserDetails();
		
		if(null!=userDetails){
			Collection<GrantedAuthority> collection = userDetails.getAuthorities();
			Iterator<GrantedAuthority> i = collection.iterator();
			Set<String> authoritySet = new HashSet<String>();
			while(i.hasNext()){
				authoritySet.add(i.next().getAuthority());
			}
			
			return authoritySet;
		}else{
			return null;
		}
	}
}
