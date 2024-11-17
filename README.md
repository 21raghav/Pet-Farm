After cloning the repository on your machine run:
```bash
git checkout -b yourusername (put your actual username)
```

This will create a branch where you can make changes.

When you are ready to make a merge request, make sure to do the following:
```bash
git checkout dev
git pull
git checkout yourusername
git merge dev -m "message"
git push
```

This will include all the new changes from the dev branch into your branch so that when your pull request is approved, nothing is corrupted.