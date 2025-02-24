// interface NotificationProps {
//   context: string;
//   time: number;
// }

const Notification = () =>
  // {
  //   notificationData,
  // }: {
  //   notificationData: NotificationProps;
  // }
  {
    return (
      <div className="flex border border-red-500 w-full p-4 gap-2 justify-between">
        <div className="flex flex-col gap-3">
          <div className="flex items-center gap-2">
            <div className="">🔔</div>
            <div className="text-blue-500 font-bold text-[10px]">
              {/* {notificationData.context} */}
              게시글에 xxx님이 지원을 신청하였습니다.
            </div>
          </div>
          <div className="">
            <div className="flex justify-center">게시물로 이동 버튼</div>
          </div>
        </div>
        <div className="flex flex-col gap-3">
          <div className="flex justify-center">❌</div>
          <div className="flex">{/* {notificationData.time} */}15분 전</div>
        </div>
      </div>
    );
  };

export default Notification;
