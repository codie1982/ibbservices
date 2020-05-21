package com.reactlibrary.helper;

public class VersionHelper {

    public Boolean checkVersion(Long current_version, Long published_version){
        if(current_version<published_version){
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkVersion(Double current_version, Double published_version){
        if(current_version < published_version){
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkVersion(String current_version, String published_version){
        int _current_version = Integer.parseInt(current_version);
        int _published_version = Integer.parseInt(published_version);

        System.out.println(_current_version+ " - "+ _published_version);
        if(_current_version < _published_version){
            return true;
        }else {
            return false;
        }
    }

}
