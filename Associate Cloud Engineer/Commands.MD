##Commands in GCP
Configurations

    gcloud config configurations create [NAME]
    gcloud config configurations activate [NAME] 
    gcloud config unset compute/zone
    gcloud config set project id
    glcoud config set compute/zone    # This applies to current project configuration and not all... 
    gcloud config get-value compute/zone

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
        
        #Reserve IP Address
        gcloud compute addresses create vpn-1-static-ip --project=qwiklabs-gcp-02-f354f5b3039a --region=us-central1


Virtual Machine :
        
        gcloud compute instances list
        gcloud compute instances create --machine-type=n1-standard-1 my-first-paid-machine
        gcloud compute instances delete my-first-paid-machine
        gcloud compute instances create privatenet-us-vm --zone=us-central1-c --machine-type=f1-micro --subnet=privatesubnet-us --image-family=debian-10 --image-project=debian-cloud --boot-disk-size=10GB --boot-disk-type=pd-standard --boot-disk-device-name=privatenet-us-vm
        gcloud compute instances list --sort-by=ZONE
        gcloud compute ssh vm-internal --zone us-central1-c --tunnel-through-iap
        gcloud projects add-iam-policy-binding PROJECT_ID --member=user:EMAIL --role=roles/iap.tunnelResourceAccessor
        
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
        
        gcloud compute project-info add-metadata --metadata google-compute-default-region=europe-west1,google-compute-default-zone=europe-west1-b
        gcloud compute project-info describe --project project-id
        
        
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
        
        
Instance group templates / Groups:
   
        gcloud compute instance-templates create mytemplate
        gcloud compute instance-templates delete mytemplate
        gcloud compute instance-templates create mytemplate --source-instance=ch06-instance-1
        gcloud compute instance-templates describe mytemplate
        gcloud compute instance-templates list
        
        #remember - always provide managed vs unmanaged as group
        gcloud compute instance-groups managed create --template=mytemplate --size=5 myinstancegroup
        
        gcloud compute instance-groups managed list-instances
        gcloud compute instance-groups managed list-instances myinstancegroup
        gcloud compute instance-groups managed delete myinstancegroup
        

             

Container Clusters :
    
        gcloud compute instances list
        gcloud container clusters create --num-nodes=4 --region=us-central1 first-k8s-cluster
        gcloud container clusters create --num-nodes 2 webfrontend
        
        gcloud container clusters get-credentials standard-cluster-1 --zone us-central1-c
        
        gcloud components install kubectl
        kubectl run ch07-app-deploy --image=ch07-app --port=8080
        kubectl create deploy nginx --image=nginx:1.17.10
        kubectl get pods
        kubectl expose deployment ngnix --type LoadBalancer --port 80
        kubectl get services
        
        kubectl scale deployment nginx --replicas 3
        kubectl autoscale deployment nginx-1 --max 10 --min 1 --cpu-percent 80
        kubectl delete deployment nginx-1
        
        kubectl describe nodes/podes
        
        kubectl run hello-server --image=gcr.io/google/samples/hello-app:1.0 --port 8080
        kubectl expose deployment hello-server --type="LoadBalancer"
        kubectl delete service hello-server
        
        
        gcloud container images list
        gcloud container images list --repository gcr.io/google-containers
        gcloud container images describe gcr.io/appengflex-project-1/nginx
        
        gcloud container clusters resize standard-cluster-1 --zone us-central1-c --num-nodes 4 --node-pool default-pool
        gcloud container clusters update standard-cluster-1 --enable-autoscaling --min-nodes 1 --max-nodes 5 --zone us-central1-c --node-pool default-pool
        
App Engine:
        
        gcloud components install app-engine-python
        
        gcloud app deploy app.yml --stop-previous-version --version=VERSION
        gcloud app browse
        
        
        gcloud app versions stop v1 v2
        gcloud app services set-traffic serv1 --splits v1=.4,v2=.6
        # IF no service name then applied to all services
        --migrate indicates that App Engine should migrate traffic from the previous version to the new version.
        --split-by specifies how to split traffic using either IP or cookies. Possible values are ip, cookie, and random.


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


    #Create Custom Roles
    gcloud iam roles create role-id --project=project-id --file=yaml-file-path

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
    gsutil ls
    gsutil rewrite -s [STORAGE_CLASS] gs://[PATH_TO_OBJECT]
    gsutil mv
    gsutil stat # get the stats like changes done 
    
    gsutil mv gs://ace-exam-bucket1/README.txt gs://ace-exam-bucket2/
    
Databases

    gcloud sql connect ace-exam-mysql 
    
    gcloud sql backups create --instance ace-exam-mysql ––async
    gcloud sql instances patch [INSTANCE_NAME] –backup-start-time [HH:MM]
    
    gcloud datastore export –namespaces='(default)' gs://ace_exam_backups
    gcloud datastore import gs://ace_exam_backups/[FILE].overall_export_metadata
    
    
    bq ––location=[LOCATION] query ––use_legacy_sql=false ––dry_run [SQL_QUERY]
    
    bq --location=US show -j gcpace-project:US.bquijob_119adae7_167c373d5c3
    
    
    cbt # cloud big table
    
    gcloud dataproc clusters create cluster-bc3d ––zone us-west2-a
    gcloud dataproc jobs submit spark ––cluster cluster-bc3d ––jar ace_exam_jar.jar
    
    
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
         
         
      kubectl autoscle rc 


Tunneling and VPN

    gcloud compute --project "qwiklabs-gcp-02-f354f5b3039a" target-vpn-gateways create "vpn-1" --region "us-central1" --network "vpn-network-1"
    
    gcloud compute --project "qwiklabs-gcp-02-f354f5b3039a" forwarding-rules create "vpn-1-rule-esp" --region "us-central1" --address "35.188.135.59" --ip-protocol "ESP" --target-vpn-gateway "vpn-1"
    
    gcloud compute --project "qwiklabs-gcp-02-f354f5b3039a" forwarding-rules create "vpn-1-rule-udp500" --region "us-central1" --address "35.188.135.59" --ip-protocol "UDP" --ports "500" --target-vpn-gateway "vpn-1"
    
    gcloud compute --project "qwiklabs-gcp-02-f354f5b3039a" forwarding-rules create "vpn-1-rule-udp4500" --region "us-central1" --address "35.188.135.59" --ip-protocol "UDP" --ports "4500" --target-vpn-gateway "vpn-1"
    
    gcloud compute --project "qwiklabs-gcp-02-f354f5b3039a" vpn-tunnels create "tunnel1to2" --region "us-central1" --peer-address "35.189.237.31" --shared-secret "gcprocks" --ike-version "2" --local-traffic-selector "0.0.0.0/0" --target-vpn-gateway "vpn-1"
    
    gcloud compute --project "qwiklabs-gcp-02-f354f5b3039a" routes create "tunnel1to2-route-1" --network "vpn-network-1" --next-hop-vpn-tunnel "tunnel1to2" --next-hop-vpn-tunnel-region "us-central1" --destination-range "10.1.3.0/24"