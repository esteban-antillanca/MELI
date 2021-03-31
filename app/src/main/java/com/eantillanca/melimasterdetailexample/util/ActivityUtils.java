package com.eantillanca.melimasterdetailexample.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by Esteban Antillanca on 2020-01-08.
 * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
 * performed by the {@code fragmentManager}.
 */
public class ActivityUtils {

        public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment fragment, int frameId) {
            checkNotNull(fragmentManager);
            checkNotNull(fragment);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }

    }

