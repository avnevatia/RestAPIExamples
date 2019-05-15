package com.ce.rest.Util;

public class Constants
{
  public static final String ELEMENTS_TOKEN = "User YMpP0oLEjqrHy9CKhh+/2TnUSY6cLNA16C5/ue5PhL4=, Organization fa4c6d3fce29c7daca28535c36883c12, Element ";
  public static final String ONE_DRIVE_TOKEN = ELEMENTS_TOKEN + "RsWQ3q/64UFETk+nM32sJD551TyBZo+5JT8tM+pxNMU=";
  public static final String GOOGLE_DRIVE_TOKEN = ELEMENTS_TOKEN + "AQBsD5BmUSTWSFn1ajZ2JOZsaSw0ZUE01YkCfesv2LE=";
  
  public static final String ELEMENTS_FILE_URL = "https://staging.cloud-elements.com/elements/api-v2/files?path=";
  public static final String ELEMENTS_GET_FOLDER_URL ="https://staging.cloud-elements.com/elements/api-v2/folders/contents?path=";
  public static final String ELEMENTS_POST_FOLDER_URL ="https://staging.cloud-elements.com/elements/api-v2/folders";

  public static final String LINE_FEED = "\r\n";
  
  public static final String LOCAL_FILE_PATH = Utillity.createTempFolder();
}
