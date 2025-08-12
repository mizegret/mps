# Devcontainer Folder (Configs Only, No Docker Runtime)

This project keeps environment configs under `.devcontainer/` **for consistency and portability**, but we are **not using Docker/Dev Containers** during local development because it’s heavy on this machine.  
Instead, we reuse the same config files directly on the host.

I’ll Dockerize it down the road—once I get a new machine. My current laptop panics at the word container.