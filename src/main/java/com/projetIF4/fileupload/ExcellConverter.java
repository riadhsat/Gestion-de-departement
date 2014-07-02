package com.projetIF4.fileupload;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

//~--- JDK imports ------------------------------------------------------------

import java.io.*;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Moez-pc
 */
public class ExcellConverter {

    /**
     *
     * @param file
     * @return
     */
    public static Vector read(InputStream file) {
        Vector cellVectorHolder = new Vector();

        try {
            POIFSFileSystem myFileSystem = new POIFSFileSystem(file);
            HSSFWorkbook    myWorkBook   = new HSSFWorkbook(myFileSystem);
            HSSFSheet       mySheet      = myWorkBook.getSheetAt(0);
            Iterator        rowIter      = mySheet.rowIterator();
            int             nbvides      = 0;

            while (rowIter.hasNext()) {
                nbvides = 0;

                HSSFRow  myRow           = (HSSFRow) rowIter.next();
                Iterator cellIter        = myRow.cellIterator();
                Vector   cellStoreVector = new Vector();

                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();

                    if (myCell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        nbvides++;
                    }

                    cellStoreVector.addElement(myCell);
                }

                if (cellStoreVector.size() - nbvides > 1) {
                    cellVectorHolder.addElement(cellStoreVector);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellVectorHolder;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
