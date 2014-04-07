*******************************************************************

* Title:  MakeFolders
* Author: [Matthew Boyette](mailto:Dyndrilliac@gmail.com)
* Date:   11/12/2012

*******************************************************************

This code makes use of my [Custom Java API](https://github.com/Dyndrilliac/java-custom-api). In order to build this source, you should clone the repository for the API using your Git client, then import the project into your IDE of choice (I prefer Eclipse), and finally modify the build path to include the API project. For more detailed instructions, see the README for the API project.

This is a simple program to take plain-text files with folder names on each line and then automatically generate the folders. I wrote this as a solution to a problem faced during a 2012-2013 internship. The company wanted to organize some network storage space and had a mechanism in their CMS software to export a plain-text list with all of their customer's account numbers but no way to automate the task of turning that list into a viable directory structure. Further, they wanted a cross-platform solution that would work on Linux, Windows, and Mac OS X.

A pre-compiled JAR binary can be downloaded from [this link](https://www.dropbox.com/s/goxpiry189ey51a/MakeFolders.jar).