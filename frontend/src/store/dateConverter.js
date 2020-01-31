export default requestDate => {
  const today = new Date();
  const convertRequestedDate = new Date(requestDate);

  const betweenTime = Math.floor(
    (today.getTime() - convertRequestedDate.getTime()) / 1000 / 60
  );
  if (betweenTime < 1) return "방금전";
  if (betweenTime < 60) {
    return `${betweenTime}분전`;
  }

  const betweenTimeHour = Math.floor(betweenTime / 60);
  if (betweenTimeHour < 24) {
    return `${betweenTimeHour}시간전`;
  }

  const splitDate = requestDate.split("T");
  const time = splitDate[1].split(".")[0].split(":");
  return splitDate[0] + ". " + time[0] + ":" + time[1];
};
