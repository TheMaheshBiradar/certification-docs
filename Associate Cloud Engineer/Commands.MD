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
        gcloud compute firewall-rules create privatenet-allow-icmp-ssh-rdp --direction=INGRESS --network=privatenet --priority=1000 --action=ALLOW --rules=icmp,tcp:22,tcp:3389 --source-ranges=0.0.0.0/0
        gcloud compute firewall-rules list
        gcloud compute firewall-rules list --sort-by=NETWORK
        gcloud compute firewall-rules list --sort-by=NETWORK --format=json
        


Virtual Machine :
    
        gcloud compute instances list
        gcloud compute instances create --machine-type=n1-standard-1 my-first-paid-machine
        gcloud compute instances delete my-first-paid-machine
        gcloud compute instances create privatenet-us-vm --zone=us-central1-c --machine-type=f1-micro --subnet=privatesubnet-us --image-family=debian-10 --image-project=debian-cloud --boot-disk-size=10GB --boot-disk-type=pd-standard --boot-disk-device-name=privatenet-us-vm
        gcloud compute instances list --sort-by=ZONE
        gcloud compute ssh vm-internal --zone us-central1-c --tunnel-through-iap
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



Screen Utilities

    sudo apt-get install -y screen
