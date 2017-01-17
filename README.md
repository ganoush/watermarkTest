# watermarkTest

Watermark Application:

Environment: MAC OS, Java 8, SpringBoot

# Application port set at 8080. This can be verriden in appplication.properties file
# Appication end points:
  /api/submit - POST Dcoument => This method accepts document, whch is of type Document, and returns a ticket which is of type long
  /api/status/{ticket} - GET  => This method returns the submitted job status for the given ticket
  /api/getDoc/{ticket} - GET  =>This method returns the document, which is of type Document, if the job to set watermark is Finished
  
  
