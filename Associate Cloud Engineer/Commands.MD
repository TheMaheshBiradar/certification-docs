##Commands in GCP
Configurations

    gcloud config configurations create [NAME]
    gcloud config configurations activate [NAME] 
    gcloud config unset compute/zone

Projects

              
    gcloud services list --project <project_id>                 # returns already enabled services though flag is not there 
    gcloud services list --project <project_id> --enabled       # already enabled
    gcloud services list --project <project_id> --available     # available to be enabled 
    
    
Create Storage bucket :

    gsutil mb gs://maheshbiradar03
    gsutil mb -b on gs://maheshbiradar03 --uniform Access on on Bucket Level
    gsutil cp myfile gs://maheshbiradar03
    gsutil mb -c standard -l us-east1 --retention 1y gs://some-bucket ## if no location the default is US

   
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
        
        gcloud compute reset-windows-password instance-name

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

    gcloud projects list  
    gcloud projects describe circular-genius-287421
    
    get users and their IAM policies
    gcloud projects get-iam-policy circular-genius-287421


Storage

    gsutil lifecycle get gs://$BUCKET_NAME_1
    nano life.json
    {
      "rule":
      [
        {
          "action": {"type": "Delete"},
          "condition": {"age": 31}
        }
      ]
    }
    gsutil lifecycle set life.json gs://$BUCKET_NAME_1
    
    
    gsutil versioning get gs://$BUCKET_NAME_1
    gsutil versioning set on gs://$BUCKET_NAME_1
    gsutil cp -v setup.html gs://$BUCKET_NAME_1
    gsutil ls -a gs://$BUCKET_NAME_1/setup.html
    gsutil rsync -r ./firstlevel gs://$BUCKET_NAME_1/firstlevel
    
Screen Utilities

    sudo apt-get install -y screen
    
Activate Service Accounts

    gcloud auth activate-service-account --key-file credentials.json

App Deployment

    gcloud app deploy app.yaml --quiet
    gcloud app browse --quiet
    
    
 App Infra/ Deployment Manager
 
    gcloud deployment-manager deployments create dminfra --config=config.yaml --preview
    gcloud deployment-manager deployments update dminfra
