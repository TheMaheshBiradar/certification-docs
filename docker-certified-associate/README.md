# Docker Certified Associate

Syllabus for the exam can be found here.


1. ADD vs COPY
    a. COPY - It takes source & destination . Source could be local file or DIR
    b. ADD - It provides same functionality as COPY but addition to that it provides below two functions
        a. URL - you can provide URL in source instead of local file or DIR
        b. Tar file - you can extract tar file directly into destionation.
        When you execute exec sh on container and go to temp folder you will below files.As you can see in ADD instruction compressed file is already in text format.

    /tmp # ls -ltr

    total 12
	
    -rwxr-xr-x    1 root     root            14 May  9 20:34 copy.txt
	
    -rwxr-xr-x    1 root     root            12 May  9 20:34 add.txt
	
    -rw-rw-rw-    1 root     root            22 May  9 20:34 compress.txt




2. Docker Inspect
    docker container inspect <container_name>


3. CMD vs ENTRYPOINT
    CMD - can be overriden with -c
    ENTRYPOINT - can not be overriden but you can append