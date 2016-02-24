/*
 * Copyright (c) 2012-2013, The Linux Foundation. All rights reserved.
 * Not a Contribution.
 *
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.telephony;


import android.content.Context;
import android.os.Message;
import android.os.RegistrantList;
import android.os.Registrant;
import android.os.Handler;
import android.os.AsyncResult;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.dataconnection.DataProfile;

/**
 * {@hide}
 */
public abstract class BaseCommands implements CommandsInterface {
    //***** Instance Variables
    protected Context mContext;
    protected RadioState mState = RadioState.RADIO_UNAVAILABLE;
    protected Object mStateMonitor = new Object();

    protected RegistrantList mRadioStateChangedRegistrants = new RegistrantList();
    protected RegistrantList mOnRegistrants = new RegistrantList();
    protected RegistrantList mAvailRegistrants = new RegistrantList();
    protected RegistrantList mOffOrNotAvailRegistrants = new RegistrantList();
    protected RegistrantList mNotAvailRegistrants = new RegistrantList();
    protected RegistrantList mCallStateRegistrants = new RegistrantList();
    protected RegistrantList mVoiceNetworkStateRegistrants = new RegistrantList();
    protected RegistrantList mDataNetworkStateRegistrants = new RegistrantList();
    protected RegistrantList mVoiceRadioTechChangedRegistrants = new RegistrantList();
    protected RegistrantList mImsNetworkStateChangedRegistrants = new RegistrantList();
    protected RegistrantList mIccStatusChangedRegistrants = new RegistrantList();
    protected RegistrantList mVoicePrivacyOnRegistrants = new RegistrantList();
    protected RegistrantList mVoicePrivacyOffRegistrants = new RegistrantList();
    protected Registrant mUnsolOemHookRawRegistrant;
    protected RegistrantList mOtaProvisionRegistrants = new RegistrantList();
    protected RegistrantList mCallWaitingInfoRegistrants = new RegistrantList();
    protected RegistrantList mDisplayInfoRegistrants = new RegistrantList();
    protected RegistrantList mSignalInfoRegistrants = new RegistrantList();
    protected RegistrantList mNumberInfoRegistrants = new RegistrantList();
    protected RegistrantList mRedirNumInfoRegistrants = new RegistrantList();
    protected RegistrantList mLineControlInfoRegistrants = new RegistrantList();
    protected RegistrantList mT53ClirInfoRegistrants = new RegistrantList();
    protected RegistrantList mT53AudCntrlInfoRegistrants = new RegistrantList();
    protected RegistrantList mRingbackToneRegistrants = new RegistrantList();
    protected RegistrantList mResendIncallMuteRegistrants = new RegistrantList();
    protected RegistrantList mCdmaSubscriptionChangedRegistrants = new RegistrantList();
    protected RegistrantList mCdmaPrlChangedRegistrants = new RegistrantList();
    protected RegistrantList mExitEmergencyCallbackModeRegistrants = new RegistrantList();
    protected RegistrantList mRilConnectedRegistrants = new RegistrantList();
    protected RegistrantList mIccRefreshRegistrants = new RegistrantList();
    protected RegistrantList mRilCellInfoListRegistrants = new RegistrantList();
    protected RegistrantList mSubscriptionStatusRegistrants = new RegistrantList();
    protected RegistrantList mSrvccStateRegistrants = new RegistrantList();
    protected RegistrantList mHardwareConfigChangeRegistrants = new RegistrantList();
    protected RegistrantList mWwanIwlanCoexistenceRegistrants = new RegistrantList();
    protected RegistrantList mSimRefreshRegistrants = new RegistrantList();
    protected RegistrantList mModemCapRegistrants = new RegistrantList();

    protected Registrant mGsmSmsRegistrant;
    protected Registrant mCdmaSmsRegistrant;
    protected Registrant mNITZTimeRegistrant;
    protected Registrant mSignalStrengthRegistrant;
    protected Registrant mUSSDRegistrant;
    protected Registrant mSmsOnSimRegistrant;
    protected Registrant mSmsStatusRegistrant;
    protected Registrant mSsnRegistrant;
    protected Registrant mCatSessionEndRegistrant;
    protected Registrant mCatProCmdRegistrant;
    protected Registrant mCatEventRegistrant;
    protected Registrant mCatCallSetUpRegistrant;
    protected Registrant mCatSendSmsResultRegistrant;
    protected Registrant mIccSmsFullRegistrant;
    protected Registrant mEmergencyCallbackModeRegistrant;
    protected Registrant mRingRegistrant;
    protected Registrant mRestrictedStateRegistrant;
    protected Registrant mGsmBroadcastSmsRegistrant;
    protected Registrant mCatCcAlphaRegistrant;
    protected Registrant mSsRegistrant;

    // MTK registrants
    protected RegistrantList mPhoneRatFamilyChangedRegistrants = new RegistrantList();
    protected RegistrantList mSessionChangedRegistrants = new RegistrantList();
    protected RegistrantList mSimMissing = new RegistrantList();
    protected RegistrantList mSimRecovery = new RegistrantList();
    protected RegistrantList mVirtualSimOn = new RegistrantList();
    protected RegistrantList mVirtualSimOff = new RegistrantList();
    protected RegistrantList mSimPlugOutRegistrants = new RegistrantList();
    protected RegistrantList mSimPlugInRegistrants = new RegistrantList();
    protected RegistrantList mCdmaCardTypeRegistrants = new RegistrantList();
    protected RegistrantList mCommonSlotNoChangedRegistrants = new RegistrantList();
    protected RegistrantList mDataAllowedRegistrants = new RegistrantList();
    protected RegistrantList mNeighboringInfoRegistrants = new RegistrantList();
    protected RegistrantList mNetworkInfoRegistrants = new RegistrantList();
    protected RegistrantList mPlmnChangeNotificationRegistrant = new RegistrantList();
    protected RegistrantList mPsNetworkStateRegistrants = new RegistrantList();
    protected Registrant mRegistrationSuspendedRegistrant;
    // M: fast dormancy.
    protected Registrant mScriResultRegistrant;
    /// M: [C2K] for eng mode
    protected RegistrantList mEngModeNetworkInfoRegistrant = new RegistrantList();
    /* C2K part start */
    protected RegistrantList mViaGpsEvent = new RegistrantList();
    protected RegistrantList mAcceptedRegistrant = new RegistrantList();
    protected RegistrantList mNetworkTypeChangedRegistrant = new RegistrantList();
    protected Registrant mUtkSessionEndRegistrant;
    protected Registrant mUtkProCmdRegistrant;
    protected Registrant mUtkEventRegistrant;
    protected RegistrantList mInvalidSimDetectedRegistrant = new RegistrantList();

    /// M: [C2K][IR] Support SVLTE IR feature. @{
    protected RegistrantList mMccMncChangeRegistrants = new RegistrantList();
    /// M: [C2K][IR] Support SVLTE IR feature. @}

    /// M: [C2K][IR][MD-IRAT] URC for GMSS RAT changed. @{
    protected RegistrantList mGmssRatChangedRegistrant = new RegistrantList();
    /// M: [C2K][IR][MD-IRAT] URC for GMSS RAT changed. @}

    /// M: [C2K] for ps type changed.
    protected RegistrantList mDataNetworkTypeChangedRegistrant = new RegistrantList();

    /// M: [C2K][MD IRAT] add IRat state change registrant.
    protected RegistrantList mIratStateChangeRegistrant = new RegistrantList();
    /* C2K part end */

    protected RegistrantList mAbnormalEventRegistrant = new RegistrantList();

    /// M: IMS ViLTe feature. @{
    protected RegistrantList mVtStatusInfoRegistrants = new RegistrantList();
    protected RegistrantList mVtRingRegistrants = new RegistrantList();
    /// @}

    // M: [C2K] [AP IRAT]
    protected RegistrantList mLteBgSearchStatusRegistrant = new RegistrantList();
    protected RegistrantList mLteEarfcnInfoRegistrant = new RegistrantList();

    // Preferred network type received from PhoneFactory.
    // This is used when establishing a connection to the
    // vendor ril so it starts up in the correct mode.
    protected int mPreferredNetworkType;
    // CDMA subscription received from PhoneFactory
    protected int mCdmaSubscription;
    // Type of Phone, GSM or CDMA. Set by CDMAPhone or GSMPhone.
    protected int mPhoneType;
    // RIL Version
    protected int mRilVersion = -1;

    // MTK states
    /* M: call control part start */
    protected boolean mbWaitingForECFURegistrants = false;
    protected Object mCfuReturnValue = null; ///* M: SS part */
    /* M: call control part end */

    protected Object mCdmaCardTypeValue = null;

    public BaseCommands(Context context) {
        mContext = context;  // May be null (if so we won't log statistics)
    }

    //***** CommandsInterface implementation

    @Override
    public RadioState getRadioState() {
        return mState;
    }

    @Override
    public void registerForRadioStateChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);

        synchronized (mStateMonitor) {
            mRadioStateChangedRegistrants.add(r);
            r.notifyRegistrant();
        }
    }

    @Override
    public void unregisterForRadioStateChanged(Handler h) {
        synchronized (mStateMonitor) {
            mRadioStateChangedRegistrants.remove(h);
        }
    }

    public void registerForImsNetworkStateChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mImsNetworkStateChangedRegistrants.add(r);
    }

    public void unregisterForImsNetworkStateChanged(Handler h) {
        mImsNetworkStateChangedRegistrants.remove(h);
    }

    @Override
    public void registerForOn(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);

        synchronized (mStateMonitor) {
            mOnRegistrants.add(r);

            if (mState.isOn()) {
                r.notifyRegistrant(new AsyncResult(null, null, null));
            }
        }
    }
    @Override
    public void unregisterForOn(Handler h) {
        synchronized (mStateMonitor) {
            mOnRegistrants.remove(h);
        }
    }


    @Override
    public void registerForAvailable(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);

        synchronized (mStateMonitor) {
            mAvailRegistrants.add(r);

            if (mState.isAvailable()) {
                r.notifyRegistrant(new AsyncResult(null, null, null));
            }
        }
    }

    @Override
    public void unregisterForAvailable(Handler h) {
        synchronized(mStateMonitor) {
            mAvailRegistrants.remove(h);
        }
    }

    @Override
    public void registerForNotAvailable(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);

        synchronized (mStateMonitor) {
            mNotAvailRegistrants.add(r);

            if (!mState.isAvailable()) {
                r.notifyRegistrant(new AsyncResult(null, null, null));
            }
        }
    }

    @Override
    public void unregisterForNotAvailable(Handler h) {
        synchronized (mStateMonitor) {
            mNotAvailRegistrants.remove(h);
        }
    }

    @Override
    public void registerForOffOrNotAvailable(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);

        synchronized (mStateMonitor) {
            mOffOrNotAvailRegistrants.add(r);

            if (mState == RadioState.RADIO_OFF || !mState.isAvailable()) {
                r.notifyRegistrant(new AsyncResult(null, null, null));
            }
        }
    }
    @Override
    public void unregisterForOffOrNotAvailable(Handler h) {
        synchronized(mStateMonitor) {
            mOffOrNotAvailRegistrants.remove(h);
        }
    }

    @Override
    public void registerForCallStateChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);

        mCallStateRegistrants.add(r);
    }

    @Override
    public void unregisterForCallStateChanged(Handler h) {
        mCallStateRegistrants.remove(h);
    }

    @Override
    public void registerForVoiceNetworkStateChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);

        mVoiceNetworkStateRegistrants.add(r);
    }

    @Override
    public void unregisterForVoiceNetworkStateChanged(Handler h) {
        mVoiceNetworkStateRegistrants.remove(h);
    }

    @Override
    public void registerForDataNetworkStateChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);

        mDataNetworkStateRegistrants.add(r);
    }

    @Override
    public void unregisterForDataNetworkStateChanged(Handler h) {
        mDataNetworkStateRegistrants.remove(h);
    }

    @Override
    public void registerForVoiceRadioTechChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mVoiceRadioTechChangedRegistrants.add(r);
    }

    @Override
    public void unregisterForVoiceRadioTechChanged(Handler h) {
        mVoiceRadioTechChangedRegistrants.remove(h);
    }

    @Override
    public void registerForIccStatusChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mIccStatusChangedRegistrants.add(r);
    }

    @Override
    public void unregisterForIccStatusChanged(Handler h) {
        mIccStatusChangedRegistrants.remove(h);
    }

    @Override
    public void setOnNewGsmSms(Handler h, int what, Object obj) {
        mGsmSmsRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnNewGsmSms(Handler h) {
        if (mGsmSmsRegistrant != null && mGsmSmsRegistrant.getHandler() == h) {
            mGsmSmsRegistrant.clear();
            mGsmSmsRegistrant = null;
        }
    }

    @Override
    public void setOnNewCdmaSms(Handler h, int what, Object obj) {
        mCdmaSmsRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnNewCdmaSms(Handler h) {
        if (mCdmaSmsRegistrant != null && mCdmaSmsRegistrant.getHandler() == h) {
            mCdmaSmsRegistrant.clear();
            mCdmaSmsRegistrant = null;
        }
    }

    @Override
    public void setOnNewGsmBroadcastSms(Handler h, int what, Object obj) {
        mGsmBroadcastSmsRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnNewGsmBroadcastSms(Handler h) {
        if (mGsmBroadcastSmsRegistrant != null && mGsmBroadcastSmsRegistrant.getHandler() == h) {
            mGsmBroadcastSmsRegistrant.clear();
            mGsmBroadcastSmsRegistrant = null;
        }
    }

    @Override
    public void setOnSmsOnSim(Handler h, int what, Object obj) {
        mSmsOnSimRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnSmsOnSim(Handler h) {
        if (mSmsOnSimRegistrant != null && mSmsOnSimRegistrant.getHandler() == h) {
            mSmsOnSimRegistrant.clear();
            mSmsOnSimRegistrant = null;
        }
    }

    @Override
    public void setOnSmsStatus(Handler h, int what, Object obj) {
        mSmsStatusRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnSmsStatus(Handler h) {
        if (mSmsStatusRegistrant != null && mSmsStatusRegistrant.getHandler() == h) {
            mSmsStatusRegistrant.clear();
            mSmsStatusRegistrant = null;
        }
    }

    @Override
    public void setOnSignalStrengthUpdate(Handler h, int what, Object obj) {
        mSignalStrengthRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnSignalStrengthUpdate(Handler h) {
        if (mSignalStrengthRegistrant != null && mSignalStrengthRegistrant.getHandler() == h) {
            mSignalStrengthRegistrant.clear();
            mSignalStrengthRegistrant = null;
        }
    }

    @Override
    public void setOnNITZTime(Handler h, int what, Object obj) {
        mNITZTimeRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnNITZTime(Handler h) {
        if (mNITZTimeRegistrant != null && mNITZTimeRegistrant.getHandler() == h) {
            mNITZTimeRegistrant.clear();
            mNITZTimeRegistrant = null;
        }
    }

    @Override
    public void setOnUSSD(Handler h, int what, Object obj) {
        mUSSDRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnUSSD(Handler h) {
        if (mUSSDRegistrant != null && mUSSDRegistrant.getHandler() == h) {
            mUSSDRegistrant.clear();
            mUSSDRegistrant = null;
        }
    }

    @Override
    public void setOnSuppServiceNotification(Handler h, int what, Object obj) {
        mSsnRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnSuppServiceNotification(Handler h) {
        if (mSsnRegistrant != null && mSsnRegistrant.getHandler() == h) {
            mSsnRegistrant.clear();
            mSsnRegistrant = null;
        }
    }

    @Override
    public void setOnCatSessionEnd(Handler h, int what, Object obj) {
        mCatSessionEndRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnCatSessionEnd(Handler h) {
        if (mCatSessionEndRegistrant != null && mCatSessionEndRegistrant.getHandler() == h) {
            mCatSessionEndRegistrant.clear();
            mCatSessionEndRegistrant = null;
        }
    }

    @Override
    public void setOnCatProactiveCmd(Handler h, int what, Object obj) {
        mCatProCmdRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnCatProactiveCmd(Handler h) {
        if (mCatProCmdRegistrant != null && mCatProCmdRegistrant.getHandler() == h) {
            mCatProCmdRegistrant.clear();
            mCatProCmdRegistrant = null;
        }
    }

    @Override
    public void setOnCatEvent(Handler h, int what, Object obj) {
        mCatEventRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnCatEvent(Handler h) {
        if (mCatEventRegistrant != null && mCatEventRegistrant.getHandler() == h) {
            mCatEventRegistrant.clear();
            mCatEventRegistrant = null;
        }
    }

    @Override
    public void setOnCatCallSetUp(Handler h, int what, Object obj) {
        mCatCallSetUpRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnCatCallSetUp(Handler h) {
        if (mCatCallSetUpRegistrant != null && mCatCallSetUpRegistrant.getHandler() == h) {
            mCatCallSetUpRegistrant.clear();
            mCatCallSetUpRegistrant = null;
        }
    }

    // For Samsung STK
    public void setOnCatSendSmsResult(Handler h, int what, Object obj) {
        mCatSendSmsResultRegistrant = new Registrant(h, what, obj);
    }

    public void unSetOnCatSendSmsResult(Handler h) {
        mCatSendSmsResultRegistrant.clear();
    }

    @Override
    public void setOnIccSmsFull(Handler h, int what, Object obj) {
        mIccSmsFullRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnIccSmsFull(Handler h) {
        if (mIccSmsFullRegistrant != null && mIccSmsFullRegistrant.getHandler() == h) {
            mIccSmsFullRegistrant.clear();
            mIccSmsFullRegistrant = null;
        }
    }

    @Override
    public void registerForIccRefresh(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mIccRefreshRegistrants.add(r);
    }
    @Override
    public void setOnIccRefresh(Handler h, int what, Object obj) {
        registerForIccRefresh(h, what, obj);
    }

    @Override
    public void setEmergencyCallbackMode(Handler h, int what, Object obj) {
        mEmergencyCallbackModeRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unregisterForIccRefresh(Handler h) {
        mIccRefreshRegistrants.remove(h);
    }
    @Override
    public void unsetOnIccRefresh(Handler h) {
        unregisterForIccRefresh(h);
    }

    @Override
    public void setOnCallRing(Handler h, int what, Object obj) {
        mRingRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnCallRing(Handler h) {
        if (mRingRegistrant != null && mRingRegistrant.getHandler() == h) {
            mRingRegistrant.clear();
            mRingRegistrant = null;
        }
    }

    @Override
    public void setOnSs(Handler h, int what, Object obj) {
        mSsRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnSs(Handler h) {
        mSsRegistrant.clear();
    }

    @Override
    public void setOnCatCcAlphaNotify(Handler h, int what, Object obj) {
        mCatCcAlphaRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnCatCcAlphaNotify(Handler h) {
        mCatCcAlphaRegistrant.clear();
    }

    @Override
    public void registerForInCallVoicePrivacyOn(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mVoicePrivacyOnRegistrants.add(r);
    }

    @Override
    public void unregisterForInCallVoicePrivacyOn(Handler h){
        mVoicePrivacyOnRegistrants.remove(h);
    }

    @Override
    public void registerForInCallVoicePrivacyOff(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mVoicePrivacyOffRegistrants.add(r);
    }

    @Override
    public void unregisterForInCallVoicePrivacyOff(Handler h){
        mVoicePrivacyOffRegistrants.remove(h);
    }

    @Override
    public void setOnRestrictedStateChanged(Handler h, int what, Object obj) {
        mRestrictedStateRegistrant = new Registrant (h, what, obj);
    }

    @Override
    public void unSetOnRestrictedStateChanged(Handler h) {
        if (mRestrictedStateRegistrant != null && mRestrictedStateRegistrant.getHandler() != h) {
            mRestrictedStateRegistrant.clear();
            mRestrictedStateRegistrant = null;
        }
    }

    @Override
    public void registerForDisplayInfo(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mDisplayInfoRegistrants.add(r);
    }

    @Override
    public void unregisterForDisplayInfo(Handler h) {
        mDisplayInfoRegistrants.remove(h);
    }

    @Override
    public void registerForCallWaitingInfo(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mCallWaitingInfoRegistrants.add(r);
    }

    @Override
    public void unregisterForCallWaitingInfo(Handler h) {
        mCallWaitingInfoRegistrants.remove(h);
    }

    @Override
    public void registerForSignalInfo(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mSignalInfoRegistrants.add(r);
    }

    public void setOnUnsolOemHookRaw(Handler h, int what, Object obj) {
        mUnsolOemHookRawRegistrant = new Registrant (h, what, obj);
    }

    public void unSetOnUnsolOemHookRaw(Handler h) {
        if (mUnsolOemHookRawRegistrant != null && mUnsolOemHookRawRegistrant.getHandler() == h) {
            mUnsolOemHookRawRegistrant.clear();
            mUnsolOemHookRawRegistrant = null;
        }
    }

    @Override
    public void unregisterForSignalInfo(Handler h) {
        mSignalInfoRegistrants.remove(h);
    }

    @Override
    public void registerForCdmaOtaProvision(Handler h,int what, Object obj){
        Registrant r = new Registrant (h, what, obj);
        mOtaProvisionRegistrants.add(r);
    }

    @Override
    public void unregisterForCdmaOtaProvision(Handler h){
        mOtaProvisionRegistrants.remove(h);
    }

    @Override
    public void registerForNumberInfo(Handler h,int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mNumberInfoRegistrants.add(r);
    }

    @Override
    public void unregisterForNumberInfo(Handler h){
        mNumberInfoRegistrants.remove(h);
    }

     @Override
    public void registerForRedirectedNumberInfo(Handler h,int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mRedirNumInfoRegistrants.add(r);
    }

    @Override
    public void unregisterForRedirectedNumberInfo(Handler h) {
        mRedirNumInfoRegistrants.remove(h);
    }

    @Override
    public void registerForLineControlInfo(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mLineControlInfoRegistrants.add(r);
    }

    @Override
    public void unregisterForLineControlInfo(Handler h) {
        mLineControlInfoRegistrants.remove(h);
    }

    @Override
    public void registerFoT53ClirlInfo(Handler h,int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mT53ClirInfoRegistrants.add(r);
    }

    @Override
    public void unregisterForT53ClirInfo(Handler h) {
        mT53ClirInfoRegistrants.remove(h);
    }

    @Override
    public void registerForT53AudioControlInfo(Handler h,int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mT53AudCntrlInfoRegistrants.add(r);
    }

    @Override
    public void unregisterForT53AudioControlInfo(Handler h) {
        mT53AudCntrlInfoRegistrants.remove(h);
    }

    @Override
    public void registerForRingbackTone(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mRingbackToneRegistrants.add(r);
    }

    @Override
    public void unregisterForRingbackTone(Handler h) {
        mRingbackToneRegistrants.remove(h);
    }

    @Override
    public void registerForResendIncallMute(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mResendIncallMuteRegistrants.add(r);
    }

    @Override
    public void unregisterForResendIncallMute(Handler h) {
        mResendIncallMuteRegistrants.remove(h);
    }

    @Override
    public void registerForCdmaSubscriptionChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mCdmaSubscriptionChangedRegistrants.add(r);
    }

    @Override
    public void unregisterForCdmaSubscriptionChanged(Handler h) {
        mCdmaSubscriptionChangedRegistrants.remove(h);
    }

    @Override
    public void registerForCdmaPrlChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mCdmaPrlChangedRegistrants.add(r);
    }

    @Override
    public void unregisterForCdmaPrlChanged(Handler h) {
        mCdmaPrlChangedRegistrants.remove(h);
    }

    @Override
    public void registerForExitEmergencyCallbackMode(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mExitEmergencyCallbackModeRegistrants.add(r);
    }

    @Override
    public void unregisterForExitEmergencyCallbackMode(Handler h) {
        mExitEmergencyCallbackModeRegistrants.remove(h);
    }

    @Override
    public void registerForHardwareConfigChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mHardwareConfigChangeRegistrants.add(r);
    }

    @Override
    public void unregisterForHardwareConfigChanged(Handler h) {
        mHardwareConfigChangeRegistrants.remove(h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerForRilConnected(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mRilConnectedRegistrants.add(r);
        if (mRilVersion != -1) {
            r.notifyRegistrant(new AsyncResult(null, new Integer(mRilVersion), null));
        }
    }

    @Override
    public void unregisterForRilConnected(Handler h) {
        mRilConnectedRegistrants.remove(h);
    }

    public void registerForSubscriptionStatusChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mSubscriptionStatusRegistrants.add(r);
    }

    public void unregisterForSubscriptionStatusChanged(Handler h) {
        mSubscriptionStatusRegistrants.remove(h);
    }

    public void registerForWwanIwlanCoexistence(Handler h, int what, Object obj) {
        mWwanIwlanCoexistenceRegistrants.addUnique(h, what, obj);
    }

    public void unregisterForWwanIwlanCoexistence(Handler h) {
        mWwanIwlanCoexistenceRegistrants.remove(h);
    }

    public void registerForSimRefreshEvent(Handler h, int what, Object obj) {
        mSimRefreshRegistrants.addUnique(h, what, obj);
    }

    public void unregisterForSimRefreshEvent(Handler h) {
        mSimRefreshRegistrants.remove(h);
    }

    public void registerForModemCapEvent(Handler h, int what, Object obj) {
        mModemCapRegistrants.addUnique(h, what, obj);
    }

    public void unregisterForModemCapEvent(Handler h) {
        mModemCapRegistrants.remove(h);
    }

    @Override
    public void getDataCallProfile(int appType, Message result) {
    }

    //***** Protected Methods
    /**
     * Store new RadioState and send notification based on the changes
     *
     * This function is called only by RIL.java when receiving unsolicited
     * RIL_UNSOL_RESPONSE_RADIO_STATE_CHANGED
     *
     * RadioState has 3 values : RADIO_OFF, RADIO_UNAVAILABLE, RADIO_ON.
     *
     * @param newState new RadioState decoded from RIL_UNSOL_RADIO_STATE_CHANGED
     */
    protected void setRadioState(RadioState newState) {
        RadioState oldState;

        synchronized (mStateMonitor) {
            oldState = mState;
            mState = newState;

            if (oldState == mState) {
                // no state transition
                return;
            }

            mRadioStateChangedRegistrants.notifyRegistrants();

            if (mState.isAvailable() && !oldState.isAvailable()) {
                mAvailRegistrants.notifyRegistrants();
                onRadioAvailable();
            }

            if (!mState.isAvailable() && oldState.isAvailable()) {
                mNotAvailRegistrants.notifyRegistrants();
            }

            if (mState.isOn() && !oldState.isOn()) {
                mOnRegistrants.notifyRegistrants();
            }

            if ((!mState.isOn() || !mState.isAvailable())
                && !((!oldState.isOn() || !oldState.isAvailable()))
            ) {
                mOffOrNotAvailRegistrants.notifyRegistrants();
            }
        }
    }

    public void sendSMSExpectMore (String smscPDU, String pdu, Message result) {
    }

    protected void onRadioAvailable() {
    }

    public void getModemCapability(Message response) {
    }

    public void updateStackBinding(int stackId, int enable, Message response) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLteOnCdmaMode() {
        return TelephonyManager.getLteOnCdmaModeStatic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerForCellInfoList(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);
        mRilCellInfoListRegistrants.add(r);
    }
    @Override
    public void unregisterForCellInfoList(Handler h) {
        mRilCellInfoListRegistrants.remove(h);
    }

    @Override
    public void registerForSrvccStateChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant (h, what, obj);

        mSrvccStateRegistrants.add(r);
    }

    @Override
    public void unregisterForSrvccStateChanged(Handler h) {
        mSrvccStateRegistrants.remove(h);
    }

    @Override
    public void testingEmergencyCall() {}

    @Override
    public int getRilVersion() {
        return mRilVersion;
    }

    public void setUiccSubscription(int slotId, int appIndex, int subId, int subStatus,
            Message response) {
    }

    public void setDataProfile(DataProfile[] dps, Message result) {
    }

    public void setDataAllowed(boolean allowed, Message response) {
    }

    @Override
    public void requestShutdown(Message result) {
    }

    @Override
    public void iccOpenLogicalChannel(String AID, Message response) {}

    @Override
    public void iccCloseLogicalChannel(int channel, Message response) {}

    @Override
    public void iccTransmitApduLogicalChannel(int channel, int cla, int instruction,
                                              int p1, int p2, int p3, String data,
                                              Message response) {}
    @Override
    public void iccTransmitApduBasicChannel(int cla, int instruction, int p1, int p2,
                                            int p3, String data, Message response) {}

    @Override
    public void getAtr(Message response) {}

    public void setLocalCallHold(int lchStatus) {
    }

    /**
     * @hide
     */
    @Override
    public int getLteOnGsmMode() {
        return TelephonyManager.getLteOnGsmModeStatic();
    }

    // MTK additions

    //MTK-START [mtk06800] modem power on/off
    @Override
    public void setModemPower(boolean power, Message response) {
    }
    //MTK-END [mtk06800] modem power on/off

    public void hangupAll(Message result) {}

    //MTK-START Support Multi-Application
    @Override
    public void openIccApplication(int application, Message response) {
    }

    @Override
    public void getIccApplicationStatus(int sessionId, Message result) {
    }

    @Override
    public void iccIOForAppEx(int command, int fileid, String path, int p1, int p2, int p3,
            String data, String pin2, String aid, int channel, Message response) {
    }

    @Override
    public void registerForSessionChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mSessionChangedRegistrants.add(r);
    }

    @Override
    public void unregisterForSessionChanged(Handler h) {
        mSessionChangedRegistrants.remove(h);
    }
    //MTK-END Support Multi-Application

    //MTK-START Support SIM ME lock
    @Override
    public void queryNetworkLock(int categrory, Message response){};

    @Override
    public void setNetworkLock(int catagory, int lockop, String password,
            String data_imsi, String gid1, String gid2, Message response){};
    //MTK-END Support SIM ME lock

    @Override
    public void doGeneralSimAuthentication(int sessionId, int mode , int tag, String param1,
                                          String param2, Message response) {
    }

    public void registerForSimMissing(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mSimMissing.add(r);
    }
    public void unregisterForSimMissing(Handler h) {
        mSimMissing.remove(h);
    }

    public void registerForSimRecovery(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mSimRecovery.add(r);
    }

    public void unregisterForSimRecovery(Handler h) {
        mSimRecovery.remove(h);
    }

    public void registerForVirtualSimOn(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mVirtualSimOn.add(r);
    }

    public void unregisterForVirtualSimOn(Handler h) {
        mVirtualSimOn.remove(h);
    }

    public void registerForVirtualSimOff(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mVirtualSimOff.add(r);
    }

    public void unregisterForVirtualSimOff(Handler h) {
        mVirtualSimOff.remove(h);
    }

    public void registerForSimPlugOut(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mSimPlugOutRegistrants.add(r);
    }

    public void unregisterForSimPlugOut(Handler h) {
        mSimPlugOutRegistrants.remove(h);
    }

    public void registerForSimPlugIn(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mSimPlugInRegistrants.add(r);
    }

    public void unregisterForSimPlugIn(Handler h) {
        mSimPlugInRegistrants.remove(h);
    }

    /**
      * Rregister for cdma card type.
      * @param h Handler for network information messages.
      * @param what User-defined message code.
      * @param obj User object.
      */
    public void registerForCdmaCardType(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mCdmaCardTypeRegistrants.add(r);

        if (mCdmaCardTypeValue != null) {
            r.notifyRegistrant(new AsyncResult(null, mCdmaCardTypeValue, null));
        }
    }

    /**
      * Rregister for cdma card type.
      * @param h Handler for network information messages.
      */
    public void unregisterForCdmaCardType(Handler h) {
        mCdmaCardTypeRegistrants.remove(h);
    }

    public void registerForCommonSlotNoChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mCommonSlotNoChangedRegistrants.add(r);
    }

    public void unregisterForCommonSlotNoChanged(Handler h) {
        mCommonSlotNoChangedRegistrants.remove(h);
    }

    public void registerSetDataAllowed(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mDataAllowedRegistrants.add(r);
    }

    public void unregisterSetDataAllowed(Handler h) {
        mDataAllowedRegistrants.remove(h);
    }

    public void registerForPsNetworkStateChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);

        mPsNetworkStateRegistrants.add(r);
    }

    public void unregisterForPsNetworkStateChanged(Handler h) {
        mPsNetworkStateRegistrants.remove(h);
    }

    public void registerForNeighboringInfo(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mNeighboringInfoRegistrants.add(r);
    }

    public void unregisterForNeighboringInfo(Handler h) {
        mNeighboringInfoRegistrants.remove(h);
    }

    public void registerForNetworkInfo(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mNetworkInfoRegistrants.add(r);
    }

    public void unregisterForNetworkInfo(Handler h) {
        mNetworkInfoRegistrants.remove(h);
    }

    public void setTrm(int mode, Message result) {}

    public void setOnPlmnChangeNotification(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mPlmnChangeNotificationRegistrant.add(r);
    }

    public void unSetOnPlmnChangeNotification(Handler h) {
        mPlmnChangeNotificationRegistrant.remove(h);
    }

    public void setOnRegistrationSuspended(Handler h, int what, Object obj) {
        mRegistrationSuspendedRegistrant = new Registrant(h, what, obj);
    }

    public void unSetOnRegistrationSuspended(Handler h) {
        mRegistrationSuspendedRegistrant.clear();
    }

    @Override
    public void setPhoneRatFamily(int ratFamily, Message response) {
    }

    @Override
    public void getPhoneRatFamily(Message response) {
    }

    @Override
    public void registerForPhoneRatFamilyChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mPhoneRatFamilyChangedRegistrants.add(r);
    }

    @Override
    public void unregisterForPhoneRatFamilyChanged(Handler h) {
        mPhoneRatFamilyChangedRegistrants.remove(h);
    }

    public void setupDataCall(String radioTechnology, String profile,
            String apn, String user, String password, String authType,
            String protocol, String interfaceId, Message result) {
    }

    // M: fast dormancy
    public void setScriResult(Handler h, int what, Object obj) {
        mScriResultRegistrant = new Registrant(h, what, obj);
    }

    public void unSetScriResult(Handler h) {
        mScriResultRegistrant.clear();
    }

    public void setScri(boolean forceRelease, Message response){
    }

    public void setFDMode(int mode, int parameter1, int parameter2, Message response){
    }

    /* C2K part start */
    @Override
    public void setViaTRM(int mode, Message result) {}

    @Override
    public void getNitzTime(Message result) {}

    @Override
    public void requestSwitchHPF(boolean enableHPF, Message response) {}

    @Override
    public void setAvoidSYS(boolean avoidSYS, Message response) {}

    @Override
    public void getAvoidSYSList(Message response) {}

    @Override
    public void queryCDMANetworkInfo(Message response) {}

    @Override
    public void setOplmn(String oplmnInfo, Message response) {
    }

    @Override
    public void getOplmnVersion(Message response) {
    }

    @Override
    public void registerForCallAccepted(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mAcceptedRegistrant.add(r);
    }

    @Override
    public void unregisterForCallAccepted(Handler h) {
        mAcceptedRegistrant.remove(h);
    }

    @Override
    public void registerForViaGpsEvent(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mViaGpsEvent.add(r);
    }

    @Override
    public void unregisterForViaGpsEvent(Handler h) {
        mViaGpsEvent.remove(h);
    }

    @Override
    public void setMeid(String meid, Message response) {}

    @Override
    public void setArsiReportThreshold(int threshold, Message response) {}

    @Override
    public void registerForNetworkTypeChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mNetworkTypeChangedRegistrant.add(r);
    }

    @Override
    public void unregisterForNetworkTypeChanged(Handler h) {
        mNetworkTypeChangedRegistrant.remove(h);
    }

    @Override
    public void queryCDMASmsAndPBStatus(Message response) {}

    @Override
    public void queryCDMANetWorkRegistrationState(Message response) {}

    @Override
    public void requestSetEtsDev(int dev, Message result) {}

    @Override
    public void requestAGPSGetMpcIpPort(Message result) {}

    @Override
    public void requestAGPSSetMpcIpPort(String ip, String port, Message result) {}

    @Override
    public void requestAGPSTcpConnected(int connected, Message result) {}

    @Override
    public void setMdnNumber(String mdn, Message response) {}

    // UTK start
    @Override
    public void setOnUtkSessionEnd(Handler h, int what, Object obj) {
        mUtkSessionEndRegistrant = new Registrant(h, what, obj);
    }

    @Override
    public void unSetOnUtkSessionEnd(Handler h) {
        mUtkSessionEndRegistrant.clear();
    }

    @Override
    public void setOnUtkProactiveCmd(Handler h, int what, Object obj) {
        mUtkProCmdRegistrant = new Registrant(h, what, obj);
    }

    @Override
    public void unSetOnUtkProactiveCmd(Handler h) {
        mUtkProCmdRegistrant.clear();
    }

    @Override
    public void setOnUtkEvent(Handler h, int what, Object obj) {
        mUtkEventRegistrant = new Registrant(h, what, obj);
    }

    @Override
    public void unSetOnUtkEvent(Handler h) {
        mUtkEventRegistrant.clear();
    }
    //UTK end

    //C2K SVLTE remote SIM access
    @Override
    public void configModemStatus(int modemStatus, int remoteSimProtocol, Message result) {}

    @Override
    public void configIratMode(int iratMode, Message result) {}
    /* C2k part end */

    public void registerForAbnormalEvent(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mAbnormalEventRegistrant.add(r);
    }

    public void unregisterForAbnormalEvent(Handler h) {
        mAbnormalEventRegistrant.remove(h);
    }

    /// M: [C2K] for eng mode start
    @Override
    public void registerForEngModeNetworkInfo(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mEngModeNetworkInfoRegistrant.add(r);
    }

    @Override
    public void unregisterForEngModeNetworkInfo(Handler h) {
        mEngModeNetworkInfoRegistrant.remove(h);
    }
    /// M: [C2K] for eng mode end

    public int getDisplayState() {
        //return Display type: Unknown display type.
        return 0;
    }

    /* M: IMS ViLTE feature part start */
    /**
     * Dial video call.
     * @param address dailing number.
     * @param clirMode indication to present the dialing number or not.
     * @param result the command result.
     * @Override
     */
    public void vtDial(String address, int clirMode, Message result) {}

    /**
     * Dial video call.
     * @param address dailing number.
     * @param clirMode indication to present the dialing number or not.
     * @param uusInfo User-User Signaling Information
     * @param result the command result.
     * @Override
     */
    public void vtDial(String address, int clirMode, UUSInfo uusInfo, Message result) {}
    /* M: IMS ViLTE feature part end */

    /* M: IMS VoLTE conference dial feature start*/
    /**
     * Dial conference call.
     * @param participants participants' dailing number.
     * @param clirMode indication to present the dialing number or not.
     * @param isVideoCall indicate this call is belong to video call or voice call.
     * @param result the command result.
     */
    public void conferenceDial(String[] participants, int clirMode,
            boolean isVideoCall, Message result) {}
    /* IMS VoLTE conference dial feature end*/

    /// M: [C2K][IR][MD-IRAT] URC for GMSS RAT changed. @{
    @Override
    public void registerForGmssRatChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mGmssRatChangedRegistrant.add(r);
    }

    @Override
    public void unregisterForGmssRatChanged(Handler h) {
        mGmssRatChangedRegistrant.remove(h);
    }
    /// M: [C2K][IR][MD-IRAT] URC for GMSS RAT changed. @}

    /// M: [C2K] for ps type changed. @{
    @Override
    public void registerForDataNetworkTypeChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mDataNetworkTypeChangedRegistrant.add(r);
    }

    @Override
    public void unregisterForDataNetworkTypeChanged(Handler h) {
        mDataNetworkTypeChangedRegistrant.remove(h);
    }
    /// @}

    /// [C2K][IRAT] @{
    @Override
    public void registerForIratStateChanged(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mIratStateChangeRegistrant.add(r);
    }

    @Override
    public void unregisterForIratStateChanged(Handler h) {
        mIratStateChangeRegistrant.remove(h);
    }

    @Override
    public void confirmIratChange(int apDecision, Message result) {

    }

    @Override
    public void requestSetPsActiveSlot(int psSlot, Message response) {
    }

    @Override
    public void syncNotifyDataCallList(AsyncResult dcList) {

    }
    /// @}

    // M: [C2K] AP IRAT start.
    @Override
    public void requestTriggerLteBgSearch(int numOfArfcn, int[] arfcn, Message response) {
    }

    @Override
    public void registerForLteBgSearchStatus(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mLteBgSearchStatusRegistrant.add(r);
    }

    @Override
    public void unregisterForLteBgSearchStatus(Handler h) {
        mLteBgSearchStatusRegistrant.remove(h);
    }

    @Override
    public void requestSetLteEarfcnEnabled(boolean enable, Message response) {
    }

    @Override
    public void registerForLteEarfcnInfo(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        mLteEarfcnInfoRegistrant.add(r);
    }

    @Override
    public void unregisterForLteEarfcnInfo(Handler h) {
        mLteEarfcnInfoRegistrant.remove(h);
    }
    // M: [C2K] AP IRAT end.

    /// M: [C2K][SVLTE] Set the SVLTE RAT mode. @{
    @Override
    public void setSvlteRatMode(int preSvlteMode, int svlteMode, int preRoamingMode,
            int roamingMode, Message response) {
    }
    /// M: [C2K][SVLTE] Set the SVLTE RAT mode. @}

    /// M: [C2K][IR] Support SVLTE IR feature. @{

    @Override
    public void setRegistrationSuspendEnabled(int enabled, Message response) {
    }

    @Override
    public void setResumeRegistration(int sessionId, Message response) {
    }

    @Override
    public void setCdmaRegistrationSuspendEnabled(boolean enabled, Message response) {
    }

    @Override
    public void setResumeCdmaRegistration(Message response) {
    }

    @Override
    public void registerForMccMncChange(Handler h, int what, Object obj) {
        // Rlog.d(RIL.RILJ_LOG_TAG, "registerForMccMncChange h=" + h + " w=" + what);
        Registrant r = new Registrant(h, what, obj);
        mMccMncChangeRegistrants.add(r);
    }

    @Override
    public void unregisterForMccMncChange(Handler h) {
        // Rlog.d(RIL.RILJ_LOG_TAG, "unregisterForMccMncChange");
        mMccMncChangeRegistrants.remove(h);
    }

    /// M: [C2K][IR] Support SVLTE IR feature. @}
}
