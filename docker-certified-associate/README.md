# Docker Certified Associate

#Setup 

If you are working on Windows Machine then you can use WSL 2 together with docker to get docker desktop.

https://docs.docker.com/docker-for-windows/wsl/

Syllabus for the exam can be found here.

Docker exam experience & questions - https://medium.com/bb-tutorials-and-thoughts/250-practice-questions-for-the-dca-exam-84f3b9e8f5ce




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


4. Search image with --filter 
    1.  docker search ngnix --filter "is-official=true"
    2. docker search ngnix --limit 5


5. How to transfer docker image

    1. docker save busybox > busybox.tar
    2. docker load < busybox.tar

6.  Build Cache : If certain step has changed and cache is not used for the step and it's subsequent layers.

7.  