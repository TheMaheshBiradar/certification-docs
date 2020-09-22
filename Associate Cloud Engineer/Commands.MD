#Commands in GCP

Create Storage bucket :

    gsutil mb gs://maheshbiradar03
    gsutil cp myfile gs://maheshbiradar03
   
   
Virtual Machine :
    
        gcloud compute instances list
        gcloud compute instances create --machine-type=n1-standard-1 my-first-paid-machine
        gcloud compute instances delete my-first-paid-machine
      
Container Clusters :
    
        gcloud compute instances list
        gcloud container clusters create --num-nodes=4 --region=us-central1 first-k8s-cluster
    

List the regions:

    gcloud compute regions list
   
IAM:
        
        gcloud organizations add-iam-policy-binding ORGANIZATION --member=MEMBER
            --role=ROLE
Importance of .profile and source in that  when you close the shell and open again


Projects :
    get users and their IAM policies
     
    gcloud projects get-iam-policy circular-genius-287421