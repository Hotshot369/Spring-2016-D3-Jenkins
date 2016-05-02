# Spring-2016-D3-Jenkins
**Objective:** D3 Visualizations published as reports by a custom Jenkins Plugin after an android project builds in Jenkins. The data visualized are test & analysis reports.

The folder named 'visualization-plugin' is supposed to be the Jenkins plugin source.  
1. Import it in IntelliJ IDEA. When prompted select 'Create project from existing sources'.  
2. If at any point there is a message like ‘non-managed pom.xml found’, just add as a maven project.  
3. Run > Edit configurations > + > Maven  
4. Give the configutation some name. E.g: MyPluginConfig  
5. In command line, set "-Dmaven.test.skip=true -DskipTests=true clean hpi:run" and click 'OK'  
6. Now debug the plugin. This will start Jenkins server for you.  

##Using the Plugin:
1. Select “New Item”.  
2. Enter your item name for e.g. ‘My Project’. This would be your project name.  
3. Select ‘Freestyle project’ and click 'OK'.  
4. You will reach the configuration page.  
5. Go to post-build actions and add ‘Publish HTML reports’ as  as a post-build action.  
6. Click the “Add” button next to “Reports”.  
7. Give your report a title or use the default one.  
8. Don’t bother about the publishing options for now.  
9. You can add more reports but in our case every report is going to be the same and so this would just create duplicate copies of the same report. Hence there is no point in adding any more reports.  
10. Click 'Save' to save the configuration.  
11. Click 'Build Now' to build the project.  
12. You will be able to see the visualization report which basically has the build no. (successful build no.) on the X-Axis. The more the no. of builds, the more bars will be visible in the Bar Chart and similarly we can say about other visualizations.  
13. Note that in every build the data in the file named 'uipassfail.xml' (in resources folder) is being added to the data which is being visualized.  


For help with Jenkins, refer https://docs.google.com/document/d/1bEPDyV2faZgWrdnlXptH3luUoMW05I2DytEIgU23ado/edit?ts=57067630
