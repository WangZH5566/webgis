<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<head>
    <script type="text/javascript">

        //Global variables
        var picWidth = 200; // width of the canvas
        var picHeight = 200; // height of the canvas
        var picLength = picWidth * picHeight; // number of chunks
        var myImage = new Image(); // Create a new blank image.

        // Load the image and display it.
        function displayImage() {

            // Get the canvas element.
            canvas = document.getElementById("myCanvas");

            // Make sure you got it.
            if (canvas.getContext) {

                // Specify 2d canvas type.
                ctx = canvas.getContext("2d");

                // When the image is loaded, draw it.
                myImage.onload = function() {
                    // Load the image into the context.
                    ctx.drawImage(myImage, 0, 0);

                    // Get and modify the image data.
                    getColorData();

                    // Put the modified image back on the canvas.
                    putColorData();
                }

                // Define the source of the image.
                // This file must be on your machine in the same folder as this web page.
                myImage.src = $("#myPhoto").attr("src");
            }
        }


        function getColorData() {

            myImage = ctx.getImageData(0, 0, 200, 200);

            // Loop through data.
            for (var i = 1; i < picLength * 4; i += 4) {

                // First bytes are red bytes.
                // Remove all red.
                myImage.data[i] = 0;

                // Second bytes are green bytes.
                // Third bytes are blue bytes.
                // Fourth bytes are alpha bytes
            }
        }

        //变灰
        //        function getColorData() {
        //
        //            myImage = ctx.getImageData(0, 0, 200, 200);
        //
        //            // Loop through data.
        //            for (var i = 0; i < picLength * 4; i += 4) {
        //
        //                // First bytes are red bytes.
        //                // Get red value.
        //                var myRed = myImage.data[i];
        //
        //                // Second bytes are green bytes.
        //                // Get green value.
        //                var myGreen = myImage.data[i + 1];
        //
        //                // Third bytes are blue bytes.
        //                // Get blue value.
        //                var myBlue = myImage.data[i + 2];
        //
        //                // Fourth bytes are alpha bytes
        //                // We don't care about alpha here.
        //                // Add the three values and divide by three.
        //                // Make it an integer.
        //                myGray = parseInt((myRed + myGreen + myBlue) / 3);
        //
        //                // Assign average to red, green, and blue.
        //                myImage.data[i] = myGray;
        //                myImage.data[i + 1] = myGray;
        //                myImage.data[i + 2] = myGray;
        //            }
        //        }


        function putColorData() {

            ctx.putImageData(myImage, 0, 0);
        }

        function noPhoto() {

            alert("Please put a .png file in this folder and name it kestral.png.");

        }

    </script>
</head>

<body onload="">

----------------------------------------------------------------------------------------------------<br/>
····················暂不提供预览！！！·····················<br/>
----------------------------------------------------------------------------------------------------<br/>
<a href="${fileVisitPath}/${file.filePath}" target="_blank" type="application/x-download">点击下载</a>
<input type="hidden" id="txt_current_directory" value="${currentDirectory}" />
<h1>
    American Kestral
</h1>
<p>
    The original image is on the left and the modified image is on the right.
</p>
<img id="myPhoto" src="${ctx}/image/kestral.png" onerror="noPhoto()">
<canvas id="myCanvas" width="200" height="200">
</canvas>
<p>
    Public domain image courtesy of U.S. Fish and Wildlife Service.
</p>

<script type="text/javascript">
    $(document).ready(function(){
        displayImage();
    });
</script>
</body>

</html>