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
     * @param premiereLigne
     * @return
     */
    public static Vector read(InputStream file, int premiereLigne) {
        Vector cellVectorHolder = new Vector();

        try {
            POIFSFileSystem myFileSystem = new POIFSFileSystem(file);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();

            //traitement de l'en-tete
            HSSFRow row = mySheet.getRow(premiereLigne);
            short firstCellNum = row.getFirstCellNum();
            Vector<Integer> indices = new Vector<>();
            for (int i = firstCellNum; i < row.getPhysicalNumberOfCells(); i++) {
                HSSFCell c = (HSSFCell) row.getCell(i);

                switch (c.getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        double k = c.getNumericCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        String champ = c.getStringCellValue();
                        if (champ.equals("CIN") || champ.equals("N° Inscription") || champ.equals("Groupe") || champ.equals("Nom et Prénom Fr")) {
                            indices.add(i);
                        }
                        break;
                }
            }

            System.out.print("Indices --> " + indices);

            for (int i = premiereLigne + 1; i < mySheet.getPhysicalNumberOfRows(); i++) {
                Vector cellStoreVector = new Vector();
                row = mySheet.getRow(i);
                for (Integer indice : indices) {
                    HSSFCell cell = row.getCell(indice);
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            cellStoreVector.addElement(cell.getNumericCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            cellStoreVector.addElement(cell.getStringCellValue());
                            break;

                    }

                }
                cellVectorHolder.addElement(cellStoreVector);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellVectorHolder;
    }

    public static Vector<String> readHeader(InputStream file, int premiereLigne) {
        Vector<String> cellVectorHolder = new Vector<>();
        try {
            POIFSFileSystem myFileSystem = new POIFSFileSystem(file);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            //traitement de l'en-tete
            HSSFRow row = mySheet.getRow(premiereLigne);
            for (int i = row.getFirstCellNum(); i < row.getPhysicalNumberOfCells(); i++) {
                HSSFCell c = (HSSFCell) row.getCell(i);
                cellVectorHolder.add(c.getStringCellValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cellVectorHolder;
    }
}



