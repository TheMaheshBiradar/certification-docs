##Commands in GCP
Configurations

    gcloud config configurations create [NAME]
    gcloud config configurations activate [NAME] 
    gcloud config unset compute/zone
    gcloud config set project id
    glcoud config set compute/zone
    

Services on Projects

              
    gcloud services list --project <project_id>                 # returns already enabled services though flag is not there 
    gcloud services list --project <project_id> --enabled       # already enabled
    gcloud services list --project <project_id> --available     # available to be enabled & already enabled 
    
    gcloud services disable my-consumed-service
    gcloud services disable my-consumed-service --async
    
    gcloud services enable service1 service2 service3
    gcloud services enable my-consumed-service --async
    
    
Create Storage bucket :

    gsutil mb gs://maheshbiradar03
    gsutil mb -b on gs://maheshbiradar03 --uniform Access on on Bucket Level
    gsutil cp myfile gs://maheshbiradar03
    gsutil mb -c standard -l us-east1 --retention 1y gs://some-bucket ## if no location the default is US

Projects -> Billing account

    gcloud beta billing projects link my-project --billing-account=0X0X0X-0X0X0X-0X0X0X
   
Virtual Networks
   
       gcloud compute networks create privatenet --subnet-mode=custom
       gcloud compute networks subnets create privatesubnet-eu --network=privatenet --region=europe-west1 --range=172.20.0.0/20
       gcloud compute networks subnets create privatesubnet-us --network=privatenet --region=us-central1 --range=172.16.0.0/24
       gcloud compute networks list
       gcloud compute networks subnets list --sort-by=NETWORK
       gcloud compute networks subnets list --sort-by=NETWORK
       gcloud compute networks subnets describe NAME --region=europe-west1  # describe specific network
       
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
        gcloud compute project-info describe #Describe the Compute Engine project resource.

        gcloud compute machine-types list
        gcloud compute disk-types list
        gcloud compute zones list
        gcloud compute instances delete ch06-instance-1 --zone us-central2-b --keepdisks=all
        gcloud compute instances delete ch06-instance-1 --zone us-central2-b --deletedisks=data
        gcloud compute instances describe INSTANCE_NAME
        
        #movement within region
        gcloud compute instances move
        
        #different regions - then follow snapshot approach
        gcloud compute instances start instance1, instance 2 --async
   
Disks / Snapshots /Images :

        gcloud compute disks snapshot DISK_NAME --snapshot-names=NAME
        gcloud compute disks create DISK_NAME --source-snapshot=SNAPSHOT_NAME
        gcloud compute snapshots list
        gcloud compute snapshots describe SNAPSHOT_NAME
        
        
       
        gcloud compute images create IMAGE_NAME
                    --source-disk
                    --source-image
                    --source-image-family
                    --source-snapshot
                    --source-uri
                              
        gcloud compute images create ch06-image-1 –-source-disk ch06-disk
        gcloud compute images delete my-image
        
        gcloud compute images export --destination-uri mydestionation --image-name myimageName

             

Container Clusters :
    
        gcloud compute instances list
        gcloud container clusters create --num-nodes=4 --region=us-central1 first-k8s-cluster
    

List the regions:

    gcloud compute regions list
   
IAM:
        
        gcloud organizations add-iam-policy-binding ORGANIZATION --member=MEMBER
            --role=ROLE
        gcloud auth list  -- active account 
Importance of .profile and source in that  when you close the shell and open again


Projects :

    gcloud projects create projectId --folder folderId --organization orgId --name --set-as-default
    gcloud projects list  
    gcloud projects describe circular-genius-287421
    gcloud iam roles update roles/ProjectUpdater --project=myproject --stage disabled
   
    # See the difference... It's add-iam-policy-binding 
    gcloud projects add-iam-policy-binding example-project-id-1 --member='user:test-user@gmail.com' --role='roles/editor'
   
    # get users and their IAM policies
    gcloud projects get-iam-policy circular-genius-287421
    
    gcloud projects get-ancestors my-project


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


Kubernetes Rolling Updates
    
    
    .spec.strategy.rollingUpdate.maxUnavailable
    
        is an optional field that specifies the maximum number of Pods that can be unavailable during the update process
        The default value is 25%.
     
     .spec.strategy.rollingUpdate.maxSurge
  
        is an optional field that specifies the maximum number of Pods that can be created over the desired number of Pods
         The default value is 25%.
