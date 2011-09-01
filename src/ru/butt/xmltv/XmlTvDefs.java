package ru.butt.xmltv;

import android.net.Uri;
import android.provider.BaseColumns;

public class XmlTvDefs {
    public static final String AUTHORITY = "ru.butt.xmltv.provider.XMLTV";                                                                                                  
    public static final String CONTENT_TYPE_DIR_PREFIX = "vnd.android.cursor.dir";
    private static final String CONTENT_TYPE_ITEM_PREFIX = "vnd.android.cursor.item";
    // This class cannot be instantiated
    private XmlTvDefs() {}                                                                                                                                                            
                                                                                                                                                                                    
    /**                                                                                                                                                                             
     * Notes table                                                                                                                                                                  
     */                                                                                                                                                                             
    public static final class CategoryColumns implements BaseColumns {                                                                                                                  
        // This class cannot be instantiated                                                                                                                                        
        private CategoryColumns() {}                                                                                                                                                    
                                                                                                                                                                                    
        /**                                                                                                                                                                         
         * The content:// style URL for this table                                                                                                                                  
         */                                                                                                                                                                         
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/category");                                                                                       
                                                                                                                                                                                    
        /**                                                                                                                                                                         
         * The MIME type of {@link #CONTENT_URI} providing a directory of notes.                                                                                                    
         */                                                                                                                                                                         
        public static final String CONTENT_TYPE = CONTENT_TYPE_DIR_PREFIX + "/vnd.xmltv.category";
                                                                                                                                                                                    
        /**                                                                                                                                                                         
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.                                                                                                  
         */                                                                                                                                                                         
        public static final String CONTENT_ITEM_TYPE = CONTENT_TYPE_ITEM_PREFIX + "/vnd.xmltv.category";
                                                                                                                                                                                    
        /**
         * The title of the category
         * <P>Type: TEXT</P>
         */
        public static final String TITLE = "title";

        /**
         * The default sort order for this table                                                                                                                                    
         */                                                                                                                                                                         
        public static final String DEFAULT_SORT_ORDER = TITLE + " ASC";
                                                                                                                                                                                    


                                                                                                         
    }     
    public static final class ProgrammeColumns implements BaseColumns {

        public static final String DESC = "desc";
        public static final String CHANNEL_ID = "ch_id";
        public static final String CATEGORY_ID = "cat_id";

        private ProgrammeColumns(){}
    	
        /**                                                                                                                                                                         
         * The content:// style URL for this table                                                                                                                                  
         */                                                                                                                                                                         
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/programme");                                                                                       
                                                                                                                                                                                    
        /**                                                                                                                                                                         
         * The MIME type of {@link #CONTENT_URI} providing a directory of notes.                                                                                                    
         */                                                                                                                                                                         
        public static final String CONTENT_TYPE = CONTENT_TYPE_DIR_PREFIX + "/vnd.xmltv.programme";
                                                                                                                                                                                    
        /**                                                                                                                                                                         
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.                                                                                                  
         */                                                                                                                                                                         
        public static final String CONTENT_ITEM_TYPE = CONTENT_TYPE_ITEM_PREFIX + "/vnd.xmltv.programme";

        /**                                                                                                                                                                         
         * The title of the programme                                                                                                                                                    
         * <P>Type: TEXT</P>                                                                                                                                                        
         */                                                                                                                                                                         
        public static final String TITLE = "title";                                                                                                                                 

        /**                                                                                                                                                                         
         * The timestamp for when the programme will start                                                                                                                              
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>                                                                                                               
         */                                                                                                                                                                         
        public static final String START = "start";                                                                                                                        
                                                                                                                                                                                    
        /**                                                                                                                                                                         
         * The timestamp for when the programme will end                                                                                                                        
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>                                                                                                               
         */                                                                                                                                                                         
        public static final String STOP = "stop";                   
    }
    public static final class ChannelColumns implements BaseColumns {
    	
        /**                                                                                                                                                                         
         * The content:// style URL for this table                                                                                                                                  
         */                                                                                                                                                                         
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/channel");                                                                                       
                                                                                                                                                                                    
        /**                                                                                                                                                                         
         * The MIME type of {@link #CONTENT_URI} providing a directory of notes.                                                                                                    
         */                                                                                                                                                                         
        public static final String CONTENT_TYPE = CONTENT_TYPE_DIR_PREFIX + "/vnd.xmltv.channel";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.                                                                                                  
         */                                                                                                                                                                         
        public static final String CONTENT_ITEM_TYPE = CONTENT_TYPE_ITEM_PREFIX+"/vnd.xmltv.channel";

        public static final String NAME = "name";
        public static final String ICON = "icon";
        public static final String DEFAULT_SORT_ORDER = NAME+" ASC";
    }
}
