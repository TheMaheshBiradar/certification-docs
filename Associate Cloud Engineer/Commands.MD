#Commands in GCP

Create Storage bucket :

    gsutil mb gs://maheshbiradar03
    gsutil cp myfile gs://maheshbiradar03
   
Virtual Networks
   
       gcloud compute networks create privatenet --subnet-mode=custom
       gcloud compute networks subnets create privatesubnet-eu --network=privatenet --region=europe-west1 --range=172.20.0.0/20
       gcloud compute networks subnets create privatesubnet-us --network=privatenet --region=us-central1 --range=172.16.0.0/24
       gcloud compute networks list
       gcloud compute networks subnets list --sort-by=NETWORK
       gcloud compute networks subnets list --sort-by=NETWORK
       
  Firewall
  
        gcloud compute --project=qwiklabs-gcp-01-248f1e39fabc firewall-rules create managementnet-allow-icmp-ssh-rdp --direction=INGRESS --priority=1000 --network=managementnet --action=ALLOW --rules=tcp:22,tcp:3389,icmp --source-ranges=0.0.0.0/0
        


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
