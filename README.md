# Image Converter

## Overview
The Image Converter project is a Java-based desktop application that allows users to convert images from various formats to JPEG, PNG, GIF, BMP, and PDF. Users can either provide an image URL or select files from their device to convert.

## Features
- Convert images from URL.
- Browse and select multiple image files for conversion.
- Supported formats: JPEG, PNG, GIF, BMP, and PDF.
- User-friendly GUI with Swing.

## Prerequisites
- Java Development Kit (JDK) 8 or higher.
- Apache PDFBox library for PDF conversion.

## File path

demo/<br>
├── src/main/java<br>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── MainGUI.java<br>
├── resources/classes<br>
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── (classes & pom.xml)<br>
└── README.md

## NOTE
- MainGUI is your main code & all the resource classes that are need are stored in the Folder resource.
- For pdf creation you need to create dependency first.
- before creation of dependency download dependencies of PDFBox.Apache from https://pdfbox.apache.org/.You can use above pom.xml file for reference. 

