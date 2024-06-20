#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Set your variables
PROJECT_ID=capstone2-guest-request
REGION=asia-southeast2
SERVICE_NAME=guest-request-api
INSTANCE_CONNECTION_NAME=capstone2-guest-request:asia-southeast2:capstone-gr-mysql
DB_USER=root
DB_PASS=taqiyya13
DB_NAME=capstone_gr_db


# Enable required services
gcloud services enable run.googleapis.com sqladmin.googleapis.com cloudbuild.googleapis.com

# Create default App Engine service account if not exists
gcloud app create --region=$REGION || echo "App Engine already exists."

# Assign Cloud SQL Client role to the default App Engine service account
gcloud projects add-iam-policy-binding $PROJECT_ID \
  --member="serviceAccount:$PROJECT_ID@appspot.gserviceaccount.com" \
  --role="roles/cloudsql.client"

# Build and push the Docker image
gcloud builds submit --tag gcr.io/$PROJECT_ID/$SERVICE_NAME

# Deploy to Cloud Run
gcloud run deploy $SERVICE_NAME \
  --image gcr.io/$PROJECT_ID/$SERVICE_NAME \
  --add-cloudsql-instances $INSTANCE_CONNECTION_NAME \
  --platform managed \
  --region $REGION \
  --allow-unauthenticated

# Set environment variables in Cloud Run
gcloud run services update $SERVICE_NAME \
  --update-env-vars DB_USER=$DB_USER,DB_PASS=$DB_PASS,DB_NAME=$DB_NAME,INSTANCE_CONNECTION_NAME=$INSTANCE_CONNECTION_NAME \
  --region $REGION

